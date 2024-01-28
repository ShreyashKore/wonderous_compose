package ui.screens.home

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
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
import kotlin.math.roundToInt
import kotlin.random.Random


@OptIn(ExperimentalResourceApi::class)
@Composable
fun WonderIllustrationBackground(
    wonder: Wonder,
) = BoxWithConstraints(Modifier.fillMaxSize().background(wonder.fgColor)) {
    val currentWonder = wonder

    val maxWidthPx = LocalDensity.current.run {
        maxWidth.roundToPx()
    }


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


    @Composable
    fun Clouds(
        wonder: Wonder
    ) {
        @Composable
        fun Cloud(
            modifier: Modifier,
        ) = AnimatedVisibility(
            currentWonder == wonder,
            modifier = modifier,
            enter = slideIn(tween(2500, delayMillis = 600)) { IntOffset(maxWidthPx, 0) } +
                    fadeIn(tween(1500, delayMillis = 500)),
            exit = slideOut(tween(1500)) { IntOffset(-maxWidthPx, 0) } + fadeOut(tween(1000)),
        ) {
            Image(
                painterResource(ImagePaths.common + "/" + "cloud-white.png"),
                contentDescription = null,
                modifier = Modifier.fillMaxSize().alpha(.3f),
            )
        }

        val random = Random(wonder.hashCode())
        val halfWidth = maxWidth.value.roundToInt() / 2

        val anim = rememberInfiniteTransition()
        repeat(3) { i ->
            val scaleX = if (i % 2 == 0) -1f else 1f
            val innerRandom = Random(i)
            val offsetX = anim.animateFloat(
                innerRandom.nextFloat() * halfWidth,
                innerRandom.nextFloat() * halfWidth,
                animationSpec = infiniteRepeatable(
                    tween(10 * maxWidthPx), // increasing time for larger width as the movement should appear subtle
                    repeatMode = RepeatMode.Reverse
                )
            )
            Cloud(
                modifier = Modifier
                    .graphicsLayer {
                        translationX = offsetX.value
                    }
                    .offset(
                        x = random.nextInt(-halfWidth, halfWidth).dp,
                        y = random.nextInt(0, 200).dp
                    )
                    .height(random.nextInt(60, 150).dp)
                    .scale(scaleX = scaleX, scaleY = 1f)
            )
        }
    }

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
        Clouds(w)
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