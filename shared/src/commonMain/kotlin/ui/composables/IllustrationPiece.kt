package ui.composables

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.slideIn
import androidx.compose.animation.slideOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import models.Wonder
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import ui.getAssetPath
import kotlin.math.roundToInt

@OptIn(ExperimentalAnimationApi::class, ExperimentalResourceApi::class)
@Composable
fun IllustrationPiece(
    currentWonder: Wonder,
    wonder: Wonder,
    imageName: String,
    swipeProgress: Float = 0f,
    modifier: Modifier,
    contentScale: ContentScale = ContentScale.Crop,
    hiddenStateOffset: () -> Offset = { Offset(1f, 1f) },
    hiddenStateScale: Float = 0.5f,
) {
    val imagePainter = painterResource(wonder.getAssetPath(imageName))
    fun getHiddenStateOffset(intSize: IntSize): IntOffset {
        val (x, y) = hiddenStateOffset()
        return IntOffset((intSize.width * x).roundToInt(), (intSize.height * y).roundToInt())
    }

    val animSpec = tween<Float>(durationMillis = 1000)
    val translationSpec = tween<IntOffset>(durationMillis = 1000)
    AnimatedVisibility(
        visible = currentWonder == wonder,
        enter = scaleIn(animSpec, initialScale = hiddenStateScale) + fadeIn(animSpec) + slideIn(
            translationSpec,
            initialOffset = ::getHiddenStateOffset
        ),
        exit = scaleOut(animSpec, targetScale = hiddenStateScale) + fadeOut(animSpec) + slideOut(
            translationSpec,
            targetOffset = ::getHiddenStateOffset
        ),
        modifier = Modifier.graphicsLayer {
            val scale = 1 + swipeProgress * .03f
            scaleX = scale
            scaleY = scale
        }.wrapContentSize(unbounded = true) then modifier,
    ) {
        Image(
            imagePainter,
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = contentScale
        )
    }
}