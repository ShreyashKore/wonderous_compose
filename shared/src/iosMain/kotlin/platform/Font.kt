package platform

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import kotlinx.coroutines.runBlocking
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.resource

val cache = mutableMapOf<String, Font>()

@OptIn(ExperimentalResourceApi::class)
@Composable
actual fun font(name: String, res: String, weight: FontWeight, style: FontStyle): Font {
    return cache.getOrPut(res) {
        val byteArray = runBlocking {
            resource("fonts/$res.ttf").readBytes()
        }
        androidx.compose.ui.text.platform.Font(
            res,
            byteArray,
            weight = weight,
            style = style
        )
    }

}