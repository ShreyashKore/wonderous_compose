package example.map

// import io.ktor.client.engine.cio.*
import androidx.compose.runtime.Composable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlin.coroutines.CoroutineContext

@Composable
internal expect fun rememberTilesRepository(
    userAgent: String,
    ioScope: CoroutineScope
): ContentRepository<Tile, TileImage>

internal fun getDispatcherIO(): CoroutineContext = Dispatchers.Default
