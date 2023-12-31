package platform

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight

@Composable
actual fun font(name: String, res: String, weight: FontWeight, style: FontStyle): Font? {
    return androidx.compose.ui.text.platform.Font("fonts/$res.ttf", weight, style)
}