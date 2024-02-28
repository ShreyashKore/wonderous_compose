import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.CanvasBasedWindow
import com.hamama.kwhi.LocalLayerContainer
import kotlinx.browser.document

@OptIn(ExperimentalComposeUiApi::class)
fun main() {
    CanvasBasedWindow(canvasElementId = "ComposeTarget") {
        CompositionLocalProvider(LocalLayerContainer provides document.getElementById("components")!!) {
            App()
        }
    }
}