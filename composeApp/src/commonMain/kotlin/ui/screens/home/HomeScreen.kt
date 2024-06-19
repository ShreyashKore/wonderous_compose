package ui.screens.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.exponentialDecay
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.AnchoredDraggableState
import androidx.compose.foundation.gestures.DraggableAnchors
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.anchoredDraggable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.PagerDefaults
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.KeyboardArrowDown
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
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
import ui.composables.AppIconButton
import ui.composables.GithubButton
import ui.composables.PreviousNextNavigation
import ui.composables.WonderTitleText
import ui.getAssetPath
import ui.mainImageName
import ui.theme.greyStrong
import ui.utils.filePainterResource
import wonderouscompose.composeapp.generated.resources.Res
import wonderouscompose.composeapp.generated.resources.icon_menu
import wonderouscompose.composeapp.generated.resources.roller_1_white
import wonderouscompose.composeapp.generated.resources.roller_2_white
import kotlin.math.roundToInt

enum class SwipeState {
    Start, End
}

/**
 * large enough number to avoid overlap in smaller screens
 */
private val MIN_PAGER_WIDTH = 1000.dp

@OptIn(
    ExperimentalFoundationApi::class,
    ExperimentalSharedTransitionApi::class,
)
@Composable
fun HomeScreen(
    initialWonder: Wonder,
    openDetailScreen: (Wonder) -> Unit,
    openTimelineScreen: (Wonder) -> Unit,
    modifier: Modifier = Modifier,
    animatedVisibilityScope: AnimatedVisibilityScope,
    sharedTransitionScope: SharedTransitionScope,
) = BoxWithConstraints(modifier) {
    val scope = rememberCoroutineScope()
    val maxWidth = maxWidth
    val maxHeight = maxHeight
    var isMenuOpen by rememberSaveable { mutableStateOf(false) }
    val pagerState =
        rememberPagerState(initialPage = 500 * Wonders.size + Wonders.indexOf(initialWonder),
            pageCount = { 1000 * Wonders.size })
    val currentWonder = Wonders[pagerState.currentPage % Wonders.size]
    val density = LocalDensity.current
    val swipeableState = remember {
        AnchoredDraggableState(
            initialValue = SwipeState.Start,
            anchors = density.run {
                DraggableAnchors {
                    SwipeState.Start at 0f
                    SwipeState.End at -150.dp.toPx()
                }
            },
            positionalThreshold = { totalDistance: Float ->
                0.5f * totalDistance
            },
            velocityThreshold = {
                density.run {
                    150.dp.toPx()
                }
            },
            snapAnimationSpec = tween(),
            decayAnimationSpec = exponentialDecay(),
            confirmValueChange = {
                if (it == SwipeState.End) openDetailScreen(currentWonder)
                true
            }
        )
    }
    val swipeProgress by remember {
        derivedStateOf {
            // `progress` resets to 1 once settled so here we return 0
            if (swipeableState.targetValue != SwipeState.End && swipeableState.progress == 1f) 0f
            else swipeableState.progress
        }
    }

    val hapticFeedback = LocalHapticFeedback.current
    LaunchedEffect(currentWonder) {
        hapticFeedback.performHapticFeedback(HapticFeedbackType.LongPress)
    }

    PreviousNextNavigation(
        onPreviousPressed = {
            scope.launch {
                pagerState.animateScrollToPage(pagerState.currentPage - 1)
            }
        },
        onNextPressed = {
            scope.launch {
                pagerState.animateScrollToPage(pagerState.currentPage + 1)
            }
        },
        enabled = maxWidth > 600.dp,
        modifier = Modifier.fillMaxSize().run {
            if (isMenuOpen) blur(12.dp) else this
        }
    ) {
        Box(
            Modifier.fillMaxSize().anchoredDraggable(
                state = swipeableState,
                orientation = Orientation.Vertical,
            ),
            contentAlignment = Alignment.Center
        ) {
            WonderIllustrationBackground(
                currentWonder = currentWonder
            )

            HorizontalPager(
                state = pagerState,
                beyondViewportPageCount = 1,
                pageSize = PageSize.Fill,
                flingBehavior = PagerDefaults.flingBehavior(
                    pagerState,
                    snapPositionalThreshold = .3f
                ),
                modifier = Modifier
                    .fillMaxHeight()
                    .requiredWidth(maxOf(maxWidth, MIN_PAGER_WIDTH))
                    .padding(top = 80.dp),
            ) { pageNo ->
                val wonder = Wonders[pageNo % Wonders.size]
                Box(Modifier.fillMaxSize()) {
                    with(sharedTransitionScope) {
                        Image(
                            filePainterResource(wonder.getAssetPath(wonder.mainImageName)),
                            contentDescription = "main",
                            modifier = Modifier.sharedBounds(
                                rememberSharedContentState("image-${wonder.title}"),
                                animatedVisibilityScope
                            ).graphicsLayer {
                                val scale = 1 - swipeProgress * .01f
                                scaleX = scale
                                scaleY = scale
                            }
                                .align(wonder.mainImageAlignment)
                                .padding(bottom = wonder.mainImageBottomPadding)
                                .wrapContentSize(unbounded = true)
                                .requiredHeight(maxHeight * wonder.mainImageFractionalHeight),
                            contentScale = ContentScale.FillHeight,
                        )
                    }
                }
            }

            WonderIllustrationForeground(
                currentWonder = currentWonder, verticalSwipeProgress = swipeProgress
            )

            Box {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Bottom,
                    modifier = Modifier.align(Alignment.BottomCenter)
                ) {
                    with(sharedTransitionScope) {
                        WonderTitleText(
                            currentWonder,
                            modifier = Modifier.sharedBounds(
                                rememberSharedContentState(currentWonder.title),
                                animatedVisibilityScope,
                                zIndexInOverlay = 1f
                            ).padding(vertical = 16.dp),
                            enableShadows = true
                        )
                    }
                    PageIndicator(
                        currentPage = pagerState.currentPage % Wonders.size,
                        totalPages = Wonders.size,
                        modifier = Modifier.padding(vertical = 24.dp).height(120.dp).fillMaxWidth()
                    )
                }


                VerticalSwipeIndicator(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(bottom = 16.dp),
                    getSwipeProgress = { swipeableState.offset.roundToInt() },
                    openDetailScreen = { openDetailScreen(currentWonder) },
                )
            }

        }
    }
    AnimatedVisibility(
        isMenuOpen,
        enter = fadeIn(),
        exit = fadeOut(),
    ) {
        HomeMenu(
            currentWonder,
            onPressBack = { isMenuOpen = false },
            onChangeWonder = {
                val nearestWonderPage =
                    pagerState.currentPage +
                            Wonders.indexOf(it) % Wonders.size -
                            pagerState.currentPage % Wonders.size
                scope.launch {
                    pagerState.scrollToPage(nearestWonderPage)
                }
                isMenuOpen = false
            },
            modifier = Modifier.fillMaxSize().background(greyStrong.copy(.4f)),
            openTimeline = { openTimelineScreen(currentWonder) },
            openCollection = {}
        )
    }
    AnimatedVisibility(
        !isMenuOpen,
        enter = fadeIn(),
        exit = fadeOut()
    ) {
        Row(
            Modifier.fillMaxSize()
                .height(72.dp)
                .padding(horizontal = 8.dp, vertical = 8.dp)
                .safeDrawingPadding()
                .align(Alignment.TopCenter)
        ) {
            AppIconButton(
                icon = Res.drawable.icon_menu,
                contentDescription = "Options",
                onClick = { isMenuOpen = true }
            )
            Spacer(Modifier.weight(1f))
            GithubButton()
        }
    }
}


@Composable
fun PageIndicator(
    currentPage: Int,
    totalPages: Int,
    modifier: Modifier = Modifier
) {
    Row(
        modifier,
        horizontalArrangement = Arrangement.Center
    ) {
        repeat(totalPages) { i ->
            val width = if (currentPage == i) 14.dp else 8.dp
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


@Composable
fun VerticalSwipeIndicator(
    modifier: Modifier,
    getSwipeProgress: () -> Int,
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
                IntOffset(0, getSwipeProgress())
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

private val Wonder.mainImageBottomPadding
    get() = when (this) {
        ChristRedeemer -> 0.dp
        else -> 140.dp
    }

private val Wonder.mainImageAlignment
    get() = when (this) {
        ChristRedeemer -> Alignment.BottomCenter
        else -> Alignment.Center
    }

private val Wonder.mainImageFractionalHeight
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

@OptIn(ExperimentalResourceApi::class)
val Wonder.bgTexture
    get() = when (this) {
        ChristRedeemer, Colosseum, MachuPicchu, Petra -> Res.drawable.roller_1_white
        ChichenItza, GreatWall, PyramidsGiza, TajMahal -> Res.drawable.roller_2_white
    }

