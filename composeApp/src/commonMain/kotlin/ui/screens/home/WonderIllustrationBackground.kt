package ui.screens.home

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideIn
import androidx.compose.animation.slideOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.BiasAlignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.times
import models.ChichenItza
import models.ChristRedeemer
import models.Colosseum
import models.GreatWall
import models.MachuPicchu
import models.Petra
import models.PyramidsGiza
import models.TajMahal
import models.Wonder
import models.Wonders
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import ui.ImagePaths
import ui.composables.BackgroundTexture
import ui.composables.IllustrationPiece
import ui.theme.fgColor
import kotlin.math.absoluteValue
import kotlin.math.sign
import kotlin.random.Random


@OptIn(ExperimentalResourceApi::class)
@Composable
fun WonderIllustrationBackground(
    wonder: Wonder,
) = BoxWithConstraints(Modifier.fillMaxSize().background(wonder.fgColor)) {
    val currentWonder = wonder

    val maxWidthPx = LocalDensity.current.run { maxWidth.roundToPx() }
    val maxHeightPx = LocalDensity.current.run { maxHeight.roundToPx() }

    @Composable
    fun CelestialBody(
        wonder: Wonder,
    ) = IllustrationPiece(
        currentWonder = currentWonder,
        wonder = wonder,
        imageName = wonder.celestialBodyImageName,
        modifier = Modifier
            .height(wonder.celestialBodyConfig.height * maxHeight)
            .align(wonder.celestialBodyConfig.alignment),
        hiddenStateOffset = { Offset(0f, wonder.celestialBodyConfig.hiddenStateYOffset) },
        hiddenStateScale = .8f
    )

    AnimatedContent(
        wonder,
        transitionSpec = { fadeIn() togetherWith fadeOut() }
    ) { wonder ->
        BackgroundTexture(
            texture = wonder.bgTexture,
            alpha = 0.3f,
            modifier = Modifier.fillMaxSize()
        )
    }

    for (w in Wonders) {
        CelestialBody(
            wonder = w,
        )
        repeat(3) { i ->
            Cloud(
                index = i,
                wonder = wonder,
                currentWonder = currentWonder,
                maxWidthPx = maxWidthPx,
                maxHeightPx = maxHeightPx
            )
        }
    }

}


@OptIn(ExperimentalResourceApi::class)
@Composable
fun Cloud(
    index: Int,
    wonder: Wonder,
    currentWonder: Wonder,
    maxWidthPx: Int,
    maxHeightPx: Int,
) {
    val anim = rememberInfiniteTransition()

    val scaleX = if (index % 2 == 0) -1f else 1f
    val random = remember { Random(wonder.hashCode() * index) }
    val cloudAnimStart = remember { random.nextDouble(-1.0, 1.0).toFloat() }
    val cloudAnimEnd =
        remember { cloudAnimStart - (random.nextDouble(0.2, 0.5) * cloudAnimStart.sign).toFloat() }
    val cloudOffsetX = remember { random.nextDouble(-1.0, 1.0) }
    val cloudOffsetY = remember { random.nextDouble(0.0, .2) }
    val cloudHeight = remember { random.nextInt(60, 150).dp }

    val offsetX = anim.animateFloat(
        cloudAnimStart, cloudAnimEnd,
        animationSpec = infiniteRepeatable(
            tween(
                (20 * maxWidthPx * (cloudAnimEnd - cloudAnimStart).absoluteValue *
                        random.nextDouble(1.0, 1.6)).toInt(),
                easing = LinearEasing
            ), // increasing time for larger width screens as the movement should appear subtle
            repeatMode = RepeatMode.Reverse
        )
    )

    AnimatedVisibility(
        currentWonder == wonder,
        modifier = Modifier
            .graphicsLayer {
                translationX = offsetX.value * maxWidthPx
            }
            .offset {
                IntOffset(
                    (cloudOffsetX * maxWidthPx / 2).toInt(),
                    (cloudOffsetY * maxHeightPx).toInt()
                )
            }
            .height(cloudHeight)
            .scale(scaleX = scaleX, scaleY = 1f),
        enter = slideIn(tween(2500, delayMillis = 600)) { IntOffset(maxWidthPx, 0) } +
                fadeIn(tween(1500, delayMillis = 500)),
        exit = slideOut(tween(1500)) { IntOffset(-maxWidthPx, 0) } + fadeOut(tween(1000)),
    ) {
        Image(
            painterResource(ImagePaths.common + "/" + "cloud-white.png"),
            contentDescription = null,
            modifier = Modifier.alpha(.1f),
        )
    }
}

private val Wonder.celestialBodyConfig: CelestialBodyConfig
    get() = when (this) {
        ChichenItza -> CelestialBodyConfig(
            name = celestialBodyImageName,
            alignment = BiasAlignment(.9f, -.2f),
            height = .3f,
            hiddenStateYOffset = 0f,
        )

        ChristRedeemer -> CelestialBodyConfig(
            name = celestialBodyImageName,
            alignment = BiasAlignment(1.1f, -.9f),
            height = .25f,
            hiddenStateYOffset = .5f,
        )

        Colosseum -> CelestialBodyConfig(
            name = celestialBodyImageName,
            alignment = BiasAlignment(.9f, -.8f),
            height = .3f,
            hiddenStateYOffset = .5f,
        )

        GreatWall -> CelestialBodyConfig(
            name = celestialBodyImageName,
            alignment = BiasAlignment(-.8f, -.75f),
            height = .3f,
            hiddenStateYOffset = .5f,
        )

        MachuPicchu -> CelestialBodyConfig(
            name = celestialBodyImageName,
            alignment = BiasAlignment(1.1f, -.8f),
            height = .2f,
            hiddenStateYOffset = .5f,
        )

        Petra -> CelestialBodyConfig(
            name = celestialBodyImageName,
            alignment = BiasAlignment(-.7f, -1.05f),
            height = .2f,
            hiddenStateYOffset = -.5f,
        )

        PyramidsGiza -> CelestialBodyConfig(
            name = celestialBodyImageName,
            alignment = BiasAlignment(.9f, -.85f),
            height = .15f,
            hiddenStateYOffset = -.5f,
        )

        TajMahal -> CelestialBodyConfig(
            name = celestialBodyImageName,
            alignment = BiasAlignment(-1.3f, -.9f),
            height = .3f,
            hiddenStateYOffset = .5f,
        )
    }

private val Wonder.celestialBodyImageName
    get() = when (this) {
        ChichenItza, TajMahal, ChristRedeemer, Colosseum, GreatWall, MachuPicchu -> "sun.png"
        Petra, PyramidsGiza -> "moon.png"
    }

private data class CelestialBodyConfig(
    val name: String,
    val alignment: Alignment,
    val height: Float,
    val hiddenStateYOffset: Float,
)