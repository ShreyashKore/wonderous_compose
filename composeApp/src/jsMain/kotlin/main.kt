import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.CanvasBasedWindow
import com.hamama.kwhi.LocalLayerContainer
import kotlinx.browser.document
import org.jetbrains.skiko.wasm.onWasmReady


@OptIn(ExperimentalComposeUiApi::class)
fun main() {
    onWasmReady {
        CanvasBasedWindow("Wonderous Compose") {
            CompositionLocalProvider(LocalLayerContainer provides document.getElementById("components")!!) {
                App()
            }
        }
    }
}
