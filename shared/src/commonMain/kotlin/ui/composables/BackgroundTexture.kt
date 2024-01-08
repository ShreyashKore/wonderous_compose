package ui.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageShader
import androidx.compose.ui.graphics.ShaderBrush
import androidx.compose.ui.graphics.TileMode
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.orEmpty
import org.jetbrains.compose.resources.rememberImageBitmap
import org.jetbrains.compose.resources.resource

@OptIn(ExperimentalResourceApi::class)
@Composable
fun BackgroundTexture(
    texture: String,
    alpha: Float = 1f,
    modifier: Modifier = Modifier
) {
    val image = resource(texture).rememberImageBitmap()

    val brush = remember(image) {
        ShaderBrush(
            ImageShader(
                image.orEmpty(),
                TileMode.Mirror,
                TileMode.Mirror,
            )
        )
    }
    Box(Modifier.background(brush, alpha = alpha) then modifier)
}