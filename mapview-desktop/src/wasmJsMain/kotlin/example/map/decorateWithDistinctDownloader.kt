package example.map

import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock


private sealed interface Message<K, T> {
    class DownloadComplete<K, T>(val key: K, val result: T) : Message<K, T>
    class DownloadFail<K, T>(val key: K, val exception: Throwable) : Message<K, T>
}

fun <K, T> ContentRepository<K, T>.decorateWithDistinctDownloader(
    scope: CoroutineScope
): ContentRepository<K, T> {
    val origin = this
    val mutex = Mutex()
    val mapKeyToRequests: MutableMap<K, MutableList<CompletableDeferred<T>>> = mutableMapOf()

    return object : ContentRepository<K, T> {
        override suspend fun loadContent(key: K): T {
            val deferred = CompletableDeferred<T>()
            val isRequestNew: Boolean

            mutex.withLock {
                isRequestNew = mapKeyToRequests.getOrPut(key) { mutableListOf() }.isEmpty()
                mapKeyToRequests[key]!!.add(deferred)
            }

            if (isRequestNew) {
                scope.launch {
                    val result = try {
                        val res = origin.loadContent(key)
                        Message.DownloadComplete(key, res)
                    } catch (e: Throwable) {
                        Message.DownloadFail(key, e)
                    }

                    mutex.withLock {
                        mapKeyToRequests[key]?.let { requests ->
                            when (result) {
                                is Message.DownloadComplete -> {
                                    requests.forEach { it.complete(result.result) }
                                }
                                is Message.DownloadFail -> {
                                    requests.forEach { it.completeExceptionally(result.exception) }
                                }
                            }
                            mapKeyToRequests.remove(key)
                        }
                    }
                }
            }
            return deferred.await()
        }
    }
}
