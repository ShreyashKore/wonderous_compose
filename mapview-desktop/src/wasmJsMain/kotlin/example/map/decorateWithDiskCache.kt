package example.map

import io.ktor.util.decodeBase64Bytes
import io.ktor.util.encodeBase64
import kotlinx.atomicfu.locks.synchronized
import kotlinx.browser.window
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlin.io.encoding.ExperimentalEncodingApi

interface CacheDir {
    fun ensureExists()

    fun dirExists(): Boolean

    fun resolve(path: String): CacheFile
}

interface CacheFile {
    fun readBytes(): ByteArray?
    fun writeBytes(bytes: ByteArray)
}

object WebCacheImpl {
    // TODO: Finalize implementation: move to something other than localStorage?
    fun create(dirName: String): CacheDir {
        window.localStorage.clear()
        return object : CacheDir {
            override fun ensureExists() {
                // Nothing to do here?
            }

            override fun dirExists(): Boolean {
                return true // Nothing to check here?
            }

            override fun resolve(path: String): CacheFile {

                val string = window.localStorage.getItem(path)?.takeIf { it.isNotBlank() }
                return WebCacheFileImpl(contentString = string, key = path)
            }

        }
    }
}

@OptIn(ExperimentalEncodingApi::class)
class WebCacheFileImpl(val contentString: String?, val key: String): CacheFile {
    override fun readBytes(): ByteArray? {
        println("Attempting to read string with len ${contentString?.length}: $contentString")
        return contentString?.let { it.decodeBase64Bytes().also { println("read is ${it.encodeBase64()}") } }
    }

    override fun writeBytes(bytes: ByteArray) {
        println("Writing under key $key: ${bytes.encodeBase64()}")
        window.localStorage.setItem(key, bytes.encodeBase64())
    }
}

fun ContentRepository<Tile, ByteArray>.decorateWithDiskCache(
    backgroundScope: CoroutineScope,
    cacheDir: CacheDir,
): ContentRepository<Tile, ByteArray> {

    class FileSystemLock

    val origin = this
    val locksCount = 100
    val locks = Array(locksCount) { FileSystemLock() }

    fun getLock(key: Tile) = locks[key.hashCode() % locksCount]

    return object : ContentRepository<Tile, ByteArray> {
        init {
            cacheDir.ensureExists()
        }

        override suspend fun loadContent(key: Tile): ByteArray {
            if (!cacheDir.dirExists()) {
                return origin.loadContent(key)
            }
            val file = with(key) {
                cacheDir.resolve("tile-$zoom-$x-$y.png")
            }

            val fromCache: ByteArray? = synchronized(getLock(key)) {
                file.readBytes()
            }

            val result = if (fromCache != null) {
                fromCache
            } else {
                println("Cache miss for $key")
                val image = origin.loadContent(key)
                backgroundScope.launch {
                    synchronized(getLock(key)) {
                        // save to cacheDir
                        file.writeBytes(image)
                    }
                }
                image
            }
            return result
        }
    }
}