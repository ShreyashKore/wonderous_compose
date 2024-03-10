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
import androidx.compose.foundation.layout.BoxWithConstraintsScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.coerceAtLeast
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
import ui.composables.BackgroundTexture
import ui.composables.IllustrationPiece
import ui.getAssetPath
import ui.theme.fgColor
import wonderouscompose.composeapp.generated.resources.Res
import wonderouscompose.composeapp.generated.resources.cloud_white
import kotlin.math.absoluteValue
import kotlin.math.sign
import kotlin.random.Random


@OptIn(ExperimentalResourceApi::class)
@Composable
fun WonderIllustrationBackground(
    currentWonder: Wonder,
) = BoxWithConstraints(Modifier.fillMaxSize().background(currentWonder.fgColor)) {
    val maxWidthPx = LocalDensity.current.run { maxWidth.roundToPx() }

    AnimatedContent(
        currentWonder,
        transitionSpec = { fadeIn() togetherWith fadeOut() }
    ) { wonder ->
        BackgroundTexture(
            texture = wonder.bgTexture,
            alpha = 0.3f,
            modifier = Modifier.fillMaxSize()
        )
    }
    // Place sun/moon and clouds for each wonders
    // Only show the ones for currentWonder
    for (wonder in Wonders) {
        val cloudConfigs = remember(wonder) { wonder.getCloudConfigs() }
        CelestialBody(
            isVisible = currentWonder == wonder,
            imagePath = wonder.getAssetPath(wonder.celestialBodyImageName),
            celestialBodyConfig = wonder.celestialBodyConfig,
        )
        cloudConfigs.forEach { cloudConfig ->
            AnimatedCloud(
                isVisible = currentWonder == wonder,
                maxWidthPx = maxWidthPx,
                cloudConfig = cloudConfig,
                maxCloudHeight = (maxHeight * .1f).coerceAtLeast(150.dp),
            )
        }
    }

}

@Composable
private fun BoxWithConstraintsScope.CelestialBody(
    imagePath: String,
    celestialBodyConfig: CelestialBodyConfig,
    isVisible: Boolean,
) = IllustrationPiece(
    isVisible = isVisible,
    imagePath = imagePath,
    modifier = Modifier
        .height(celestialBodyConfig.height * maxHeight)
        .align(celestialBodyConfig.alignment),
    hiddenStateOffset = { Offset(0f, celestialBodyConfig.hiddenStateYOffset) },
    hiddenStateScale = .8f
)


@OptIn(ExperimentalResourceApi::class)
@Composable
private fun BoxWithConstraintsScope.AnimatedCloud(
    isVisible: Boolean,
    maxWidthPx: Int,
    maxCloudHeight: Dp,
    cloudConfig: CloudConfig
) {
    val anim = rememberInfiniteTransition()
    val animationDuration = remember {
        (50 * maxWidthPx * cloudConfig.duration).toInt()
    }

    val offsetX = anim.animateFloat(
        cloudConfig.animationStart, cloudConfig.animationEnd,
        animationSpec = infiniteRepeatable(
            tween(animationDuration, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        )
    )

    AnimatedVisibility(
        isVisible,
        modifier = Modifier
            .graphicsLayer {
                translationX = offsetX.value * maxWidthPx
            }
            .align(BiasAlignment(cloudConfig.horizontalAlignment, cloudConfig.verticalAlignment))
            .height(cloudConfig.height * maxCloudHeight)
            .scale(scaleX = if (cloudConfig.flipHorizontally) -1f else 1f, scaleY = 1f),
        // enter and exit from distance twice the width of screen
        enter = slideIn(tween(2500, delayMillis = 600)) { IntOffset(maxWidthPx * 2, 0) } +
                fadeIn(tween(1500, delayMillis = 500)),
        exit = slideOut(tween(1500)) { IntOffset(-maxWidthPx * 2, 0) } + fadeOut(tween(1000)),
    ) {
        Image(
            painterResource(Res.drawable.cloud_white),
            contentDescription = null,
            modifier = Modifier.alpha(.4f).fillMaxSize()
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

/**
 * Configuration object for the clouds shown on home screen
 */
private data class CloudConfig(
    val height: Float,
    val horizontalAlignment: Float,
    val verticalAlignment: Float,
    val flipHorizontally: Boolean,
    val animationStart: Float,
    val animationEnd: Float,
    val duration: Float
)

private const val CLOUD_COUNT = 3

/**
 * Returns list of [CloudConfig]s with randomised values using [Wonder]'s hashcode as seed.
 */
private fun Wonder.getCloudConfigs(): List<CloudConfig> {
    val random = Random(this.hashCode())
    return buildList {
        repeat(CLOUD_COUNT) {
            val animStart = random.nextDouble(-.5, .5).toFloat()
            val animEnd =
                animStart - (animStart + animStart.sign * random.nextDouble(.6, 1.5).toFloat())
            val duration =
                random.nextDouble(.5, 1.0).toFloat() * ((animEnd - animStart).absoluteValue)
            add(
                CloudConfig(
                    height = random.nextDouble(.5, 1.5).toFloat(),
                    horizontalAlignment = random.nextDouble(-1.2, 1.2).toFloat(),
                    verticalAlignment = random.nextDouble(-1.2, -.4).toFloat(),
                    flipHorizontally = random.nextBoolean(),
                    animationStart = animStart,
                    animationEnd = animEnd,
                    duration = duration
                )
            )
        }
    }
}
