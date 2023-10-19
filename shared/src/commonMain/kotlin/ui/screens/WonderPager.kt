package ui.screens

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.slideIn
import androidx.compose.animation.slideOut
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
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
import ui.getAssetPath
import ui.mainImageName
import ui.theme.bgColor
import kotlin.math.absoluteValue
import kotlin.math.roundToInt


enum class Screens {
    Home, Details
}

enum class SwipeState {
    Start, Mid, End
}

@OptIn(
    ExperimentalAnimationApi::class, ExperimentalFoundationApi::class,
    ExperimentalResourceApi::class, ExperimentalMaterialApi::class
)
@Composable
fun SharedAnimationContainer(
    initialWonder: Wonder = ChichenItza,
    openHomeScreen: Boolean = true
) = BoxWithConstraints {
    val pagerState = rememberPagerState(initialPage = Wonders.indexOf(initialWonder) * 100_000)
    val currentWonder = Wonders[pagerState.currentPage % Wonders.size]

    var currentScreen by remember {
        mutableStateOf(if (openHomeScreen) Screens.Home else Screens.Details)
    }

    val horizontalOffset = pagerState.currentPageOffsetFraction.absoluteValue

    val maxWidth = maxWidth
    val maxHeight = maxHeight
    val smallestDim = minOf(maxWidth, maxHeight)

    fun Modifier.height(fraction: Float) = requiredHeight(smallestDim * fraction)

    fun Modifier.width(fraction: Float) = requiredWidth(smallestDim * fraction)

    fun Modifier.offset(x: Float, y: Float) = offset(smallestDim * x, smallestDim * y)

    AnimatedContent(targetState = currentScreen) {
        when (it) {
            Screens.Home -> {

                Box(
                    Modifier.fillMaxSize()
                ) {
//                    WonderIllustrationFg(
//                        wonder = currentWonder,
//                        swipeProgress = 1f
//                    )

                    HorizontalPager(
                        state = pagerState,
                        pageCount = Int.MAX_VALUE,
                        beyondBoundsPageCount = 1,
                        modifier = Modifier.fillMaxSize().background(Color.Blue),
                    ) { pageNo ->
                        val wonder = Wonders[pageNo % Wonders.size]
                        Box(Modifier.fillMaxSize()) {
                            Image(
                                painterResource(wonder.getAssetPath(wonder.mainImageName)),
                                contentDescription = "main",
                                modifier = Modifier
                                    .align(if (wonder == ChristRedeemer) Alignment.BottomCenter else Alignment.Center)
                                    .fillMaxHeight(wonder.fractionalScale),
                                contentScale = ContentScale.FillHeight,
                            )
                        }
                    }

                    WonderIllustrationFg(
                        wonder = currentWonder,
                        swipeProgress = 1f
                    )
                }
            }

            Screens.Details -> WonderDetailsScreen(
                wonder = currentWonder,
                onPressHome = { currentScreen = Screens.Home },
            )
        }
    }
}

private val Wonder.fractionalScale
    get() = when (this) {
        ChichenItza -> .3f
        ChristRedeemer -> 1f
        Colosseum -> .55f
        GreatWall -> .65f
        MachuPicchu -> .55f
        Petra -> .65f
        PyramidsGiza -> .45f
        TajMahal -> .55f
    }


@Composable
fun WonderIllustrationFg(
    wonder: Wonder,
    swipeProgress: Float,
) = BoxWithConstraints(Modifier.fillMaxSize().drawWithContent {
    drawContent()
    drawRect(
        Brush.Companion.verticalGradient(
            0f to Color.Transparent, 1f to wonder.bgColor.copy(0.5f)
        ),
    )
}) {
    val maxWidth = maxWidth
    val maxHeight = maxHeight
    val smallestDim = minOf(maxWidth, maxHeight)

    fun Modifier.height(fraction: Float) = requiredHeight(smallestDim * fraction)

    fun Modifier.width(fraction: Float) = requiredWidth(smallestDim * fraction)

    fun Modifier.offset(x: Float, y: Float) = offset(smallestDim * x, smallestDim * y)

    println(smallestDim)
    // Wall
    IllustrationPiece(
        wonder = GreatWall,
        imageName = "foreground-right.png",
        swipeProgress = swipeProgress,
        modifier = Modifier.align(Alignment.BottomEnd)
            .width(1.6f)
            .offset(.8f, .8f),
        currentWonder = wonder,
        hiddenStateOffset = { Offset(.3f, 1f) }
    )
    IllustrationPiece(
        wonder = GreatWall,
        imageName = "foreground-left.png",
        swipeProgress = swipeProgress,
        modifier = Modifier.align(Alignment.BottomStart)
            .width(1.6f)
            .offset(-.9f, .8f),
        currentWonder = wonder,
        hiddenStateOffset = { Offset(-.3f, 1f) }
    )

    // Petra
    IllustrationPiece(
        wonder = Petra,
        imageName = "foreground-right.png",
        swipeProgress = swipeProgress,
        modifier = Modifier.align(Alignment.CenterEnd)
            .requiredHeight(maxHeight)
            .offset(x = .7f, y = 0f),
        currentWonder = wonder,
        contentScale = ContentScale.FillHeight,
        hiddenStateOffset = { Offset(.3f, 0f) },
        hiddenStateScale = 1f
    )
    IllustrationPiece(
        wonder = Petra,
        imageName = "foreground-left.png",
        swipeProgress = swipeProgress,
        modifier = Modifier.align(Alignment.CenterStart)
            .requiredHeight(maxHeight)
            .offset(x = -.7f, y = 0f),
        currentWonder = wonder,
        contentScale = ContentScale.FillHeight,
        hiddenStateOffset = { Offset(-.3f, 0f) },
        hiddenStateScale = 1f
    )


    // Colosseum
    IllustrationPiece(
        wonder = Colosseum,
        imageName = "foreground-left.png",
        swipeProgress = swipeProgress,
        modifier = Modifier.align(Alignment.BottomStart)
            .height(1.5f)
            .offset(-.3f, .3f),
        currentWonder = wonder,
        hiddenStateOffset = { Offset(-.3f, 1f) }
    )
    IllustrationPiece(
        wonder = Colosseum,
        imageName = "foreground-right.png",
        swipeProgress = swipeProgress,
        modifier = Modifier.align(Alignment.BottomEnd)
            .height(1.5f)
            .offset(.3f, .4f),
        currentWonder = wonder,
        hiddenStateOffset = { Offset(.3f, 1f) }
    )

    // ChichenItza
    IllustrationPiece(
        wonder = ChichenItza,
        imageName = "top-left.png",
        swipeProgress = swipeProgress,
        modifier = Modifier.align(Alignment.TopStart)
            .height(1.2f)
            .offset(-.5f, -.4f),
        currentWonder = wonder,
        hiddenStateOffset = { Offset(-.3f, -1f) }
    )
    IllustrationPiece(
        wonder = ChichenItza,
        imageName = "top-right.png",
        swipeProgress = swipeProgress,
        modifier = Modifier.align(Alignment.TopEnd)
            .height(1.2f)
            .offset(.4f, -.5f),
        currentWonder = wonder,
        hiddenStateOffset = { Offset(.3f, -1f) }
    )
    IllustrationPiece(
        wonder = ChichenItza,
        imageName = "foreground-left.png",
        swipeProgress = swipeProgress,
        modifier = Modifier.align(Alignment.BottomStart)
            .height(1.2f)
            .offset(-.4f, .2f),
        currentWonder = wonder,
        hiddenStateOffset = { Offset(-.3f, 1f) }
    )
    IllustrationPiece(
        wonder = ChichenItza,
        imageName = "foreground-right.png",
        swipeProgress = swipeProgress,
        modifier = Modifier.align(Alignment.BottomEnd)
            .height(.8f)
            .offset(.3f, -.1f),
        currentWonder = wonder,
        hiddenStateOffset = { Offset(.3f, 1f) }
    )

    // MachuPicchu
    IllustrationPiece(
        wonder = MachuPicchu,
        imageName = "foreground-back.png",
        swipeProgress = swipeProgress,
        modifier = Modifier.align(Alignment.BottomCenter)
            .height(1f),
        currentWonder = wonder,
        hiddenStateOffset = { Offset(0f, 1f) }
    )
    IllustrationPiece(
        wonder = MachuPicchu,
        imageName = "foreground-front.png",
        swipeProgress = swipeProgress,
        modifier = Modifier.align(Alignment.BottomCenter)
            .height(1.2f)
            .offset(-.5f, .45f),
        currentWonder = wonder,
        hiddenStateOffset = { Offset(0f, 1f) }
    )


    // Taj Mahal
    IllustrationPiece(
        wonder = TajMahal,
        imageName = "foreground-right.png",
        swipeProgress = swipeProgress,
        modifier = Modifier.align(Alignment.BottomEnd)
            .height(1f)
            .offset(.15f, -.1f),
        currentWonder = wonder,
        hiddenStateOffset = { Offset(.5f, 1f) }
    )
    IllustrationPiece(
        wonder = TajMahal,
        imageName = "foreground-left.png",
        swipeProgress = swipeProgress,
        modifier = Modifier.align(Alignment.BottomStart)
            .height(1f)
            .offset(-.1f, -.1f),
        currentWonder = wonder,
        hiddenStateOffset = { Offset(-.5f, 1f) }
    )

    // Christ
    IllustrationPiece(
        wonder = ChristRedeemer,
        imageName = "foreground-left.png",
        swipeProgress = swipeProgress,
        modifier = Modifier.align(Alignment.BottomStart)
            .height(1.2f)
            .offset(-.4f, -.1f),
        currentWonder = wonder,
        hiddenStateOffset = { Offset(-.4f, 0f) }
    )
    IllustrationPiece(
        wonder = ChristRedeemer,
        imageName = "foreground-right.png",
        swipeProgress = swipeProgress,
        modifier = Modifier.align(Alignment.BottomEnd)
            .height(1.2f)
            .offset(.5f, .2f),
        currentWonder = wonder,
        hiddenStateOffset = { Offset(.4f, 0f) }
    )

    // Pyramids
    IllustrationPiece(
        wonder = PyramidsGiza,
        imageName = "foreground-back.png",
        swipeProgress = swipeProgress,
        modifier = Modifier.align(Alignment.BottomCenter)
            .height(1f)
            .offset(.6f, 0f),
        currentWonder = wonder,
        hiddenStateOffset = { Offset(.3f, 1f) }
    )
    IllustrationPiece(
        wonder = PyramidsGiza,
        imageName = "foreground-front.png",
        swipeProgress = swipeProgress,
        modifier = Modifier.align(Alignment.BottomCenter)
            .height(1f)
            .offset(-.2f, 0f),
        currentWonder = wonder,
        hiddenStateOffset = { Offset(-.3f, 1f) }
    )

}


@Composable
fun WonderIllustrationBg(
    wonder: Wonder,
    horizontalOffset: Float,
) {

//    when (wonder) {
//        ChichenItza -> ChichenItzaIllustration(horizontalOffset)
//        ChristRedeemer -> PyramidsGizaIllustration(horizontalOffset)
//        Colosseum -> GreatWallIllustration(horizontalOffset)
//        GreatWall -> PetraIllustration(horizontalOffset)
//        MachuPicchu -> ColosseumIllustration(horizontalOffset)
//        Petra -> MachuPicchuIllustration(horizontalOffset)
//        PyramidsGiza -> TajMahalIllustration(horizontalOffset)
//        TajMahal -> ChristRedeemerIllustration(horizontalOffset)
//    }
}


@OptIn(ExperimentalResourceApi::class)
@Composable
fun PyramidsGizaIllustration(
    swipeProgress: Float,
) = CompositionLocalProvider(LocalWonder provides PyramidsGiza) {

}

@OptIn(ExperimentalResourceApi::class)
@Composable
fun GreatWallIllustration(
    swipeProgress: Float,
) = CompositionLocalProvider(LocalWonder provides GreatWall) {

}

@OptIn(ExperimentalResourceApi::class)
@Composable
fun PetraIllustration(
    swipeProgress: Float,
) = CompositionLocalProvider(LocalWonder provides Petra) {

}

@OptIn(ExperimentalResourceApi::class)
@Composable
fun ColosseumIllustration(
    swipeProgress: Float,
) = CompositionLocalProvider(LocalWonder provides Colosseum) {

}

@OptIn(ExperimentalResourceApi::class)
@Composable
fun MachuPicchuIllustration(
    swipeProgress: Float,
) = CompositionLocalProvider(LocalWonder provides MachuPicchu) {

}

@OptIn(ExperimentalResourceApi::class)
@Composable
fun TajMahalIllustration(
    swipeProgress: Float,
) = CompositionLocalProvider(LocalWonder provides TajMahal) {

}

@OptIn(ExperimentalResourceApi::class)
@Composable
fun ChristRedeemerIllustration(
    swipeProgress: Float,
) = CompositionLocalProvider(LocalWonder provides ChristRedeemer) {

}

@OptIn(ExperimentalAnimationApi::class, ExperimentalResourceApi::class)
@Composable
private fun IllustrationPiece(
    currentWonder: Wonder,
    wonder: Wonder,
    imageName: String,
    swipeProgress: Float,
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
        modifier = Modifier.wrapContentSize(unbounded = true) then modifier,
    ) {
        Image(
            imagePainter,
            contentDescription = null,
            modifier = Modifier.fillMaxSize().scale(1f)
                .graphicsLayer {
//                    scaleX = (1 * swipeProgress)
                },
            contentScale = contentScale
        )
    }
}

val LocalWonder = compositionLocalOf<Wonder> {
    error("No Default")
}