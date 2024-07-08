package example.map

// import io.ktor.client.engine.cio.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import io.ktor.client.HttpClient
import io.ktor.client.plugins.UserAgent
import kotlinx.coroutines.CoroutineScope
import java.io.File

@Composable
internal actual fun rememberTilesRepository(
    userAgent: String,
    ioScope: CoroutineScope
): ContentRepository<Tile, TileImage> = remember {
    val cacheDir = File(System.getProperty("java.io.tmpdir")).resolve(Config.CACHE_DIR_NAME)
    createRealRepository(HttpClient/*(CIO)*/ {
        install(UserAgent) {
            agent = userAgent
        }
    })
        .decorateWithLimitRequestsInParallel(ioScope)
        .decorateWithDiskCache(ioScope, cacheDir)
        .adapter { TileImage(it.toImageBitmap()) }
        .decorateWithDistinctDownloader(ioScope)
}
