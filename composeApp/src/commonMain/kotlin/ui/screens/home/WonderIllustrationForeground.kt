package ui.screens.home

import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
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
import ui.composables.IllustrationPiece
import ui.composables.fractionalOffset
import ui.getAssetPath
import ui.theme.bgColor

@Composable
fun WonderIllustrationForeground(
    currentWonder: Wonder,
    verticalSwipeProgress: Float,
) {
    BoxWithConstraints(
        Modifier.fillMaxSize().drawWithContent {
            val gradientTopStop = .4f - verticalSwipeProgress * .2f
            val gradientBottomStop = 10f - verticalSwipeProgress * .2f
            val gradientBottomAlpha = (.9f + verticalSwipeProgress * .2f).coerceIn(0f, 1f)
            drawContent()
            drawRect(
                Brush.verticalGradient(
                    gradientTopStop to Color.Transparent,
                    gradientBottomStop to currentWonder.bgColor.copy(gradientBottomAlpha)
                ),
            )
        },
    ) {
        for (wonder in Wonders) {
            for (config in wonder.foregroundIllustrations) {
                IllustrationPiece(
                    imagePath = wonder.getAssetPath(config.imageName),
                    isVisible = currentWonder == wonder,
                    verticalSwipeProgress = verticalSwipeProgress,
                    modifier = Modifier
                        .fractionalOffset(config.fractionalOffset.x, config.fractionalOffset.y)
                        .align(config.alignment)
                        .requiredHeight(maxHeight * config.height),
                    hiddenStateOffset = { config.hiddenStateOffset },
                    hiddenStateScale = config.hiddenStateScale,
                )
            }
        }
    }
}


private val Wonder.foregroundIllustrations: List<FgIllustrationConfig>
    get() = when (this) {
        ChichenItza -> listOf(
            FgIllustrationConfig(
                imageName = "top-left.png",
                alignment = Alignment.TopStart,
                height = 0.6f,
                fractionalOffset = Offset(-.3f, -.35f),
                hiddenStateOffset = Offset(-.3f, -.3f),
                hiddenStateScale = .5f
            ),
            FgIllustrationConfig(
                imageName = "top-right.png",
                alignment = Alignment.TopEnd,
                height = 0.6f,
                fractionalOffset = Offset(.2f, -.35f),
                hiddenStateOffset = Offset(.3f, -.3f),
                hiddenStateScale = .5f
            ),
            FgIllustrationConfig(
                imageName = "foreground-left.png",
                alignment = Alignment.BottomStart,
                height = 0.5f,
                fractionalOffset = Offset(-.2f, .2f),
                hiddenStateOffset = Offset(-.3f, .3f),
                hiddenStateScale = .5f
            ),
            FgIllustrationConfig(
                imageName = "foreground-right.png",
                alignment = Alignment.BottomEnd,
                height = 0.3f,
                fractionalOffset = Offset(.1f, -.2f),
                hiddenStateOffset = Offset(.3f, .3f),
                hiddenStateScale = .5f
            )
        )

        ChristRedeemer -> listOf(
            FgIllustrationConfig(
                imageName = "foreground-right.png",
                alignment = Alignment.BottomEnd,
                height = 0.6f,
                fractionalOffset = Offset(.3f, .2f),
                hiddenStateOffset = Offset(.4f, 0f),
                hiddenStateScale = .5f
            ),
            FgIllustrationConfig(
                imageName = "foreground-left.png",
                alignment = Alignment.BottomStart,
                height = .7f,
                fractionalOffset = Offset(-.3f, .1f),
                hiddenStateOffset = Offset(-.4f, 0f),
                hiddenStateScale = 0.5f
            ),
        )

        Colosseum -> listOf(
            FgIllustrationConfig(
                imageName = "foreground-left.png",
                alignment = Alignment.BottomStart,
                height = 0.65f,
                fractionalOffset = Offset(-.2f, .3f),
                hiddenStateOffset = Offset(-.3f, .3f),
                hiddenStateScale = 0.5f
            ),
            FgIllustrationConfig(
                imageName = "foreground-right.png",
                alignment = Alignment.BottomEnd,
                height = .6f,
                fractionalOffset = Offset(.3f, .4f),
                hiddenStateOffset = Offset(.3f, .3f),
                hiddenStateScale = 0.5f
            )
        )

        GreatWall -> listOf(
            FgIllustrationConfig(
                imageName = "foreground-right.png",
                alignment = Alignment.BottomEnd,
                height = .8f,
                fractionalOffset = Offset(.4f, .3f),
                hiddenStateOffset = Offset(.3f, .4f),
                hiddenStateScale = .5f
            ),
            FgIllustrationConfig(
                imageName = "foreground-left.png",
                alignment = Alignment.BottomStart,
                height = .75f,
                fractionalOffset = Offset(-.4f, .4f),
                hiddenStateOffset = Offset(-.3f, .4f),
                hiddenStateScale = .5f
            )
        )

        MachuPicchu -> listOf(
            FgIllustrationConfig(
                imageName = "foreground-back.png",
                alignment = Alignment.BottomEnd,
                height = 0.45f,
                fractionalOffset = Offset(0f, 0f),
                hiddenStateOffset = Offset(0f, .1f),
                hiddenStateScale = 1f
            ),
            FgIllustrationConfig(
                imageName = "foreground-front.png",
                alignment = Alignment.BottomStart,
                height = 0.5f,
                fractionalOffset = Offset(-.2f, .3f),
                hiddenStateOffset = Offset(0f, .1f),
                hiddenStateScale = 1f
            ),
        )

        Petra -> listOf(
            FgIllustrationConfig(
                imageName = "foreground-right.png",
                alignment = Alignment.CenterEnd,
                height = 1f,
                fractionalOffset = Offset(.6f, 0f),
                hiddenStateOffset = Offset(.3f, 0f),
                hiddenStateScale = 1f
            ),
            FgIllustrationConfig(
                imageName = "foreground-left.png",
                alignment = Alignment.CenterStart,
                height = 1f,
                fractionalOffset = Offset(-.6f, 0f),
                hiddenStateOffset = Offset(-.3f, 0f),
                hiddenStateScale = 1f
            )
        )

        PyramidsGiza -> listOf(
            FgIllustrationConfig(
                imageName = "foreground-back.png",
                alignment = Alignment.BottomCenter,
                height = 0.65f,
                fractionalOffset = Offset(.3f, .1f),
                hiddenStateOffset = Offset(.2f, .3f),
                hiddenStateScale = .5f
            ),
            FgIllustrationConfig(
                imageName = "foreground-front.png",
                alignment = Alignment.BottomCenter,
                height = .6f,
                fractionalOffset = Offset(-.1f, .1f),
                hiddenStateOffset = Offset(-.2f, .3f),
                hiddenStateScale = 0.5f
            ),
        )

        TajMahal -> listOf(
            FgIllustrationConfig(
                imageName = "foreground-right.png",
                alignment = Alignment.BottomEnd,
                height = 0.5f,
                fractionalOffset = Offset(.25f, .3f),
                hiddenStateOffset = Offset(.5f, .5f),
                hiddenStateScale = .5f
            ),
            FgIllustrationConfig(
                imageName = "foreground-left.png",
                alignment = Alignment.BottomStart,
                height = 0.6f,
                fractionalOffset = Offset(-.25f, .3f),
                hiddenStateOffset = Offset(-.5f, .5f),
                hiddenStateScale = 0.5f
            ),
        )
    }

private data class FgIllustrationConfig(
    val imageName: String,
    val alignment: Alignment,
    val height: Float,
    val fractionalOffset: Offset,
    val hiddenStateOffset: Offset,
    val hiddenStateScale: Float,
)