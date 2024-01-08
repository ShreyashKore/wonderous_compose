package ui.screens.home

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.tween
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.KeyboardArrowDown
import androidx.compose.material.swipeable
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
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
import ui.composables.WonderTitleText
import ui.getAssetPath
import ui.mainImageName
import ui.screens.SharedScreen
import kotlin.math.roundToInt

@OptIn(
    ExperimentalFoundationApi::class, ExperimentalResourceApi::class, ExperimentalMaterialApi::class
)
@Composable
fun HomeScreen(
    currentWonder: Wonder,
    pagerState: PagerState,
    swipeableState: SwipeableState<SharedScreen>,
    openDetailScreen: () -> Unit,
    modifier: Modifier = Modifier,
) = BoxWithConstraints(modifier) {
    val swipeProgress by remember {
        derivedStateOf {
            // `progress.fraction` resets to 1 so here we return 0
            if (swipeableState.progress.from == SharedScreen.Home && swipeableState.progress.to == SharedScreen.Home) 0f
            else swipeableState.progress.fraction
        }
    }
    val maxHeight = maxHeight

    Box(
        Modifier.fillMaxSize().swipeable(
            state = swipeableState,
            orientation = Orientation.Vertical,
            anchors = mapOf(0f to SharedScreen.Home, -300f to SharedScreen.Details)
        )

    ) {
        WonderIllustrationBackground(
            wonder = currentWonder
        )

        HorizontalPager(
            state = pagerState,
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
                        .wrapContentSize(unbounded = true)
                        .requiredHeight(maxHeight * wonder.fractionalScale),
                    contentScale = ContentScale.FillHeight,
                )
            }
        }

        WonderIllustrationForeground(
            wonder = currentWonder, verticalSwipeProgress = swipeProgress
        )

        Box {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Bottom,
                modifier = Modifier.align(Alignment.BottomCenter)
            ) {
                WonderTitleText(
                    currentWonder,
                    modifier = Modifier.padding(vertical = 16.dp),
                    enableShadows = true
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

