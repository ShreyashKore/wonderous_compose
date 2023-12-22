package platform

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.skiko.loadBytesFromPath

val cache = mutableMapOf<String, Font>()

@OptIn(ExperimentalResourceApi::class)
@Composable
actual fun font(name: String, res: String, weight: FontWeight, style: FontStyle): Font? {
    var fontFamily by remember { mutableStateOf<Font?>(null) }
    LaunchedEffect(Unit) {
        val byteArray =
            loadBytesFromPath("fonts/$res.ttf")
        fontFamily = androidx.compose.ui.text.platform.Font(
            res,
            byteArray,
            weight = weight,
            style = style
        )
    }
    return fontFamily?.also {
        cache.getOrPut(res) { it }
    }
}