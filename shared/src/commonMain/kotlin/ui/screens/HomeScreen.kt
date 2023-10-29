package ui.screens

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideIn
import androidx.compose.animation.slideOut
import androidx.compose.animation.with
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.SwipeableState
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.KeyboardArrowDown
import androidx.compose.material.swipeable
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.BiasAlignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
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
import ui.composables.IllustrationPiece
import ui.getAssetPath
import ui.mainImageName
import ui.theme.bgColor
import ui.theme.fgColor
import kotlin.math.roundToInt
import kotlin.random.Random

@OptIn(
    ExperimentalFoundationApi::class, ExperimentalResourceApi::class, ExperimentalMaterialApi::class
)
@Composable
fun HomeScreen(
    currentWonder: Wonder,
    pagerState: PagerState,
    swipeableState: SwipeableState<SharedScreen>,
    openDetailScreen: () -> Unit,
) = BoxWithConstraints {
    val swipeProgress by remember {
        derivedStateOf {
            if (swipeableState.progress.from == SharedScreen.Home && swipeableState.progress.to == SharedScreen.Home) 0f
            else swipeableState.progress.fraction
        }
    }
    val maxHeight = maxHeight

    fun Modifier.requiredHeight(fraction: Float) = requiredHeight(maxHeight * fraction)

    Box(
        Modifier.fillMaxSize().swipeable(
            swipeableState, orientation = Orientation.Vertical, anchors = mapOf(
                0f to SharedScreen.Home,
                -300f to SharedScreen.Details,
            )
        )

    ) {
        WonderIllustrationBg(
            wonder = currentWonder
        )

        HorizontalPager(
            state = pagerState,
            pageCount = Int.MAX_VALUE,
            beyondBoundsPageCount = 1,
            pageSpacing = 1000.dp, // large enough number to avoid overlap
            modifier = Modifier.fillMaxSize().padding(top = 80.dp),
            key = { it }
        ) { pageNo ->
            val wonder = Wonders[pageNo % Wonders.size]
            Box(Modifier.fillMaxSize()) {
                Image(
                    painterResource(wonder.getAssetPath(wonder.mainImageName)),
                    contentDescription = "main",
                    modifier = Modifier.graphicsLayer {
                        val scale = 1 - swipeProgress * .01f
                        scaleX = scale
                        scaleY = scale
                    }
                        .align(if (wonder == ChristRedeemer) Alignment.BottomCenter else Alignment.Center)
                        .padding(bottom = if (wonder == ChristRedeemer) 0.dp else 140.dp)
                        .wrapContentSize(unbounded = true).requiredHeight(wonder.fractionalScale),
                    contentScale = ContentScale.FillHeight,
                )
            }
        }

        WonderIllustrationFg(
            wonder = currentWonder, swipeProgress = swipeProgress
        )

        Box {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Bottom,
                modifier = Modifier.align(Alignment.BottomCenter)
            ) {
                Text(
                    currentWonder.title,
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onPrimary,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(vertical = 16.dp)
                )
                HorizontalSwipeDots(
                    currentDot = pagerState.currentPage % Wonders.size,
                    totalDots = Wonders.size,
                    modifier = Modifier.padding(vertical = 24.dp).height(120.dp).fillMaxWidth()
                )
            }

            VerticalSwipeIndicator(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 16.dp),
                swipeableState = swipeableState,
                openDetailScreen = openDetailScreen,
            )
        }

    }
}


@Composable
fun WonderIllustrationFg(
    wonder: Wonder,
    swipeProgress: Float,
) = BoxWithConstraints(
    Modifier.fillMaxSize().drawWithContent {
        val gradientTopStop = .4f - swipeProgress * .2f
        val gradientBottomStop = 10f - swipeProgress * .2f
        val gradientBottomAlpha = (.9f + swipeProgress * .2f).coerceIn(0f, 1f)
        drawContent()
        drawRect(
            Brush.verticalGradient(
                gradientTopStop to Color.Transparent,
                gradientBottomStop to wonder.bgColor.copy(gradientBottomAlpha)
            ),
        )
    },
) {
    val maxWidth = maxWidth
    val maxHeight = maxHeight
    val smallestDim = minOf(maxWidth, maxHeight)

    fun Modifier.height(fraction: Float) = requiredHeight(smallestDim * fraction)

    fun Modifier.width(fraction: Float) = requiredWidth(smallestDim * fraction)

    fun Modifier.offset(x: Float, y: Float) = offset(smallestDim * x, smallestDim * y)

    // Wall
    IllustrationPiece(wonder = GreatWall,
        imageName = "foreground-right.png",
        swipeProgress = swipeProgress,
        modifier = Modifier.align(Alignment.BottomEnd).width(1.6f).offset(.7f, .6f),
        currentWonder = wonder,
        hiddenStateOffset = { Offset(.3f, .4f) })
    IllustrationPiece(wonder = GreatWall,
        imageName = "foreground-left.png",
        swipeProgress = swipeProgress,
        modifier = Modifier.align(Alignment.BottomStart).width(1.6f).offset(-.8f, .95f),
        currentWonder = wonder,
        hiddenStateOffset = { Offset(-.3f, .4f) })

    // Petra
    IllustrationPiece(
        wonder = Petra,
        imageName = "foreground-right.png",
        swipeProgress = swipeProgress,
        modifier = Modifier.align(Alignment.CenterEnd).requiredHeight(maxHeight)
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
        modifier = Modifier.align(Alignment.CenterStart).requiredHeight(maxHeight)
            .offset(x = -.7f, y = 0f),
        currentWonder = wonder,
        contentScale = ContentScale.FillHeight,
        hiddenStateOffset = { Offset(-.3f, 0f) },
        hiddenStateScale = 1f
    )


    // Colosseum
    IllustrationPiece(wonder = Colosseum,
        imageName = "foreground-left.png",
        swipeProgress = swipeProgress,
        modifier = Modifier.align(Alignment.BottomStart).height(1.5f).offset(-.3f, .3f),
        currentWonder = wonder,
        hiddenStateOffset = { Offset(-.3f, .3f) })
    IllustrationPiece(wonder = Colosseum,
        imageName = "foreground-right.png",
        swipeProgress = swipeProgress,
        modifier = Modifier.align(Alignment.BottomEnd).height(1.5f).offset(.3f, .4f),
        currentWonder = wonder,
        hiddenStateOffset = { Offset(.3f, .3f) })

    // ChichenItza
    IllustrationPiece(wonder = ChichenItza,
        imageName = "top-left.png",
        swipeProgress = swipeProgress,
        modifier = Modifier.align(Alignment.TopStart).height(1.2f).offset(-.5f, -.4f),
        currentWonder = wonder,
        hiddenStateOffset = { Offset(-.3f, -.3f) })
    IllustrationPiece(wonder = ChichenItza,
        imageName = "top-right.png",
        swipeProgress = swipeProgress,
        modifier = Modifier.align(Alignment.TopEnd).height(1.2f).offset(.4f, -.5f),
        currentWonder = wonder,
        hiddenStateOffset = { Offset(.3f, -.3f) })
    IllustrationPiece(wonder = ChichenItza,
        imageName = "foreground-left.png",
        swipeProgress = swipeProgress,
        modifier = Modifier.align(Alignment.BottomStart).height(1.2f).offset(-.4f, .2f),
        currentWonder = wonder,
        hiddenStateOffset = { Offset(-.3f, .3f) })
    IllustrationPiece(wonder = ChichenItza,
        imageName = "foreground-right.png",
        swipeProgress = swipeProgress,
        modifier = Modifier.align(Alignment.BottomEnd).height(.8f).offset(.3f, -.1f),
        currentWonder = wonder,
        hiddenStateOffset = { Offset(.3f, .3f) })

    // MachuPicchu
    IllustrationPiece(
        wonder = MachuPicchu,
        imageName = "foreground-back.png",
        swipeProgress = swipeProgress,
        modifier = Modifier.align(Alignment.BottomCenter).height(1f),
        currentWonder = wonder,
        hiddenStateOffset = { Offset(0f, .1f) },
        hiddenStateScale = 1f
    )
    IllustrationPiece(
        wonder = MachuPicchu,
        imageName = "foreground-front.png",
        swipeProgress = swipeProgress,
        modifier = Modifier.align(Alignment.BottomCenter).height(1.2f).offset(-.5f, .45f),
        currentWonder = wonder,
        hiddenStateOffset = { Offset(0f, .1f) },
        hiddenStateScale = 1f
    )


    // Taj Mahal
    IllustrationPiece(wonder = TajMahal,
        imageName = "foreground-right.png",
        swipeProgress = swipeProgress,
        modifier = Modifier.align(Alignment.BottomEnd).height(1f).offset(.15f, -.1f),
        currentWonder = wonder,
        hiddenStateOffset = { Offset(.5f, .5f) })
    IllustrationPiece(wonder = TajMahal,
        imageName = "foreground-left.png",
        swipeProgress = swipeProgress,
        modifier = Modifier.align(Alignment.BottomStart).height(1f).offset(-.1f, -.1f),
        currentWonder = wonder,
        hiddenStateOffset = { Offset(-.5f, .5f) })

    // Christ
    IllustrationPiece(wonder = ChristRedeemer,
        imageName = "foreground-left.png",
        swipeProgress = swipeProgress,
        modifier = Modifier.align(Alignment.BottomStart).height(1.2f).offset(-.4f, -.1f),
        currentWonder = wonder,
        hiddenStateOffset = { Offset(-.4f, 0f) })
    IllustrationPiece(wonder = ChristRedeemer,
        imageName = "foreground-right.png",
        swipeProgress = swipeProgress,
        modifier = Modifier.align(Alignment.BottomEnd).height(1.2f).offset(.5f, .2f),
        currentWonder = wonder,
        hiddenStateOffset = { Offset(.4f, 0f) })

    // Pyramids
    IllustrationPiece(
        wonder = PyramidsGiza,
        imageName = "foreground-back.png",
        swipeProgress = swipeProgress,
        modifier = Modifier.align(Alignment.BottomCenter).height(1f).offset(.6f, 0f),
        currentWonder = wonder,
        hiddenStateOffset = { Offset(.2f, .3f) },
        hiddenStateScale = .6f
    )
    IllustrationPiece(
        wonder = PyramidsGiza,
        imageName = "foreground-front.png",
        swipeProgress = swipeProgress,
        modifier = Modifier.align(Alignment.BottomCenter).height(1f).offset(-.2f, 0f),
        currentWonder = wonder,
        hiddenStateOffset = { Offset(-.2f, .3f) },
        hiddenStateScale = .6f
    )

}

@OptIn(ExperimentalResourceApi::class, ExperimentalAnimationApi::class)
@Composable
fun WonderIllustrationBg(
    wonder: Wonder,
) = BoxWithConstraints(Modifier.fillMaxSize().background(wonder.fgColor)) {
    val currentWonder = wonder
    val smallestSide = minOf(maxWidth, maxHeight)

    val maxWidthPx = LocalDensity.current.run {
        maxWidth.roundToPx()
    }

    fun Wonder.celestialAlignment(): BiasAlignment = when (this) {
        ChichenItza -> BiasAlignment(.9f, -.2f)
        ChristRedeemer -> BiasAlignment(1.1f, -.9f)
        Colosseum -> BiasAlignment(.9f, -.8f)
        GreatWall -> BiasAlignment(-.8f, -.75f)
        MachuPicchu -> BiasAlignment(1.1f, -.8f)
        Petra -> BiasAlignment(-.7f, -1.05f)
        PyramidsGiza -> BiasAlignment(.9f, -.85f)
        TajMahal -> BiasAlignment(-1.3f, -.9f)
    }

    fun Wonder.celestialSize() = smallestSide * when (this) {
        ChichenItza -> .4f
        ChristRedeemer -> .5f
        Colosseum -> .45f
        GreatWall -> .5f
        MachuPicchu -> .3f
        Petra -> .3f
        PyramidsGiza -> .35f
        TajMahal -> .6f
    }

    fun Wonder.celestialBodyHiddenYOffset() = when (this) {
        ChichenItza -> 0f
        TajMahal, ChristRedeemer, Colosseum, GreatWall, MachuPicchu -> .5f
        Petra, PyramidsGiza -> -.5f
    }

    @Composable
    fun CelestialBody(
        wonder: Wonder,
    ) = IllustrationPiece(
        currentWonder = currentWonder,
        wonder = wonder,
        imageName = wonder.celestialBodyImageName,
        modifier = Modifier
            .width(wonder.celestialSize())
            .align(wonder.celestialAlignment()),
        hiddenStateOffset = { Offset(0f, wonder.celestialBodyHiddenYOffset()) },
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
                animationSpec = infiniteRepeatable(tween(5000), repeatMode = RepeatMode.Reverse)
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
        transitionSpec = { fadeIn() with fadeOut() }
    ) { wonder ->
        Image(
            painterResource(wonder.bgTexture),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            colorFilter = ColorFilter.tint(wonder.bgTextureColor),
            modifier = Modifier.fillMaxSize().scale(1.5f)
        )
    }

    for (w in Wonders) {
        CelestialBody(
            wonder = w,
        )
        Clouds(w)
    }

}


@Composable
fun HorizontalSwipeDots(
    currentDot: Int,
    totalDots: Int,
    modifier: Modifier = Modifier
) {
    Row(
        modifier,
        horizontalArrangement = Arrangement.Center
    ) {
        repeat(totalDots) { i ->
            val width = if (currentDot == i) 14.dp else 8.dp
            Box(
                modifier = Modifier
                    .animateContentSize(tween(500))
                    .padding(4.dp)
                    .clip(CircleShape)
                    .background(Color.White)
                    .height(8.dp)
                    .width(width)
            )
        }
    }
}


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun VerticalSwipeIndicator(
    modifier: Modifier,
    swipeableState: SwipeableState<SharedScreen>,
    openDetailScreen: () -> Unit
) {
    Box(modifier, contentAlignment = Alignment.BottomCenter) {
        Box(
            Modifier.fillMaxHeight().background(
                Brush.verticalGradient(
                    0f to Color.Transparent,
                    1f to Color.White
                ),
            ).clip(RoundedCornerShape(bottomEnd = 20.dp, bottomStart = 20.dp)),
        )
        IconButton(
            modifier = Modifier.offset {
                IntOffset(
                    0, swipeableState.offset.value.roundToInt()
                )
            },
            onClick = openDetailScreen,
        ) {
            Icon(
                Icons.Sharp.KeyboardArrowDown,
                contentDescription = "Open Details",
                tint = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier.size(52.dp)
            )
        }
    }
}

private val Wonder.fractionalScale
    get() = when (this) {
        ChichenItza -> .4f
        ChristRedeemer -> .95f
        Colosseum -> .6f
        GreatWall -> .7f
        MachuPicchu -> .65f
        Petra -> .75f
        PyramidsGiza -> .5f
        TajMahal -> .55f
    }


val Wonder.bgTextureColor
    get() = when (this) {
        ChichenItza -> Color(0xffDC762A)
        ChristRedeemer -> Color(0xffFAE5C8)
        Colosseum -> Color(0xFFFFFFFF)
        GreatWall -> Color(0xff8fad78)
        MachuPicchu -> Color(0xff688750)
        Petra -> Color(0xFF444B9B)
        PyramidsGiza -> Color(0xFF797FD8)
        TajMahal -> Color(0xFFC96454)
    }

val Wonder.bgTexture
    get() = when (this) {
        ChristRedeemer, Colosseum, MachuPicchu, Petra -> ImagePaths.roller1
        ChichenItza, GreatWall, PyramidsGiza, TajMahal -> ImagePaths.roller2
    }

private val Wonder.celestialBodyImageName
    get() = when (this) {
        ChichenItza, TajMahal, ChristRedeemer, Colosseum, GreatWall, MachuPicchu -> "sun.png"
        Petra, PyramidsGiza -> "moon.png"
    }