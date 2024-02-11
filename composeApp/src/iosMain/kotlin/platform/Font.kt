package platform

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import kotlinx.coroutines.runBlocking
import org.jetbrains.compose.resources.InternalResourceApi
import org.jetbrains.compose.resources.readResourceBytes

val cache = mutableMapOf<String, Font>()

@OptIn(InternalResourceApi::class)
@Composable
actual fun font(name: String, res: String, weight: FontWeight, style: FontStyle): Font? {
    return cache.getOrPut(res) {
        val byteArray = runBlocking {
            readResourceBytes("fonts/$res.ttf")
        }
        androidx.compose.ui.text.platform.Font(
            res,
            byteArray,
            weight = weight,
            style = style
        )
    }

}