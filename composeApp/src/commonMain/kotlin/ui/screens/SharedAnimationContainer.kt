package ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.AnchoredDraggableState
import androidx.compose.foundation.gestures.DraggableAnchors
import androidx.compose.foundation.gestures.animateTo
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import models.GreatWall
import models.Wonder
import models.Wonders
import org.jetbrains.compose.resources.ExperimentalResourceApi
import ui.composables.AppIconButton
import ui.composables.GithubButton
import ui.screens.home.HomeMenu
import ui.screens.home.HomeScreen
import ui.theme.greyStrong
import wonderouscompose.composeapp.generated.resources.Res
import wonderouscompose.composeapp.generated.resources.icon_menu


enum class SharedScreen {
    Home, Details
}

/**
 * Container around [HomeScreen] and [WonderDetailsScreen]
 */
@OptIn(
    ExperimentalFoundationApi::class, ExperimentalResourceApi::class,
)
@Composable
fun SharedAnimationContainer(
    initialWonder: Wonder = GreatWall,
    openTimelineScreen: (wonder: Wonder?) -> Unit,
    openArtifactDetailsScreen: (id: String) -> Unit,
    openArtifactListScreen: (wonder: Wonder) -> Unit,
    openMapScreen: (wonder: Wonder) -> Unit,
    openVideoScreen: (videoId: String) -> Unit,
    openHomeScreen: Boolean = true,
) = BoxWithConstraints {

    var isMenuOpen by rememberSaveable { mutableStateOf(false) }
    val pagerState =
        rememberPagerState(initialPage = 500 * Wonders.size + Wonders.indexOf(initialWonder),
            pageCount = { 1000 * Wonders.size })
    val currentWonder = Wonders[pagerState.currentPage % Wonders.size]

    val density = LocalDensity.current

    val swipeableState = remember {
        AnchoredDraggableState(
            initialValue = if (openHomeScreen) SharedScreen.Home else SharedScreen.Details,
            anchors = density.run {
                DraggableAnchors {
                    SharedScreen.Home at 0f
                    SharedScreen.Details at -150.dp.toPx()
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
            animationSpec = tween()
        )
    }

    val scope = rememberCoroutineScope()

    val hapticFeedback = LocalHapticFeedback.current
    LaunchedEffect(swipeableState.targetValue) {
        if (swipeableState.targetValue == SharedScreen.Details)
            hapticFeedback.performHapticFeedback(HapticFeedbackType.LongPress)
    }
    LaunchedEffect(currentWonder) {
        hapticFeedback.performHapticFeedback(HapticFeedbackType.LongPress)
    }

    HomeScreen(
        currentWonder = currentWonder,
        pagerState = pagerState,
        swipeableState = swipeableState,
        openDetailScreen = {
            scope.launch {
                swipeableState.animateTo(SharedScreen.Details)
            }
        },
        modifier = Modifier.run {
            if (isMenuOpen) blur(12.dp) else this
        }
    )

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

    AnimatedVisibility(
        visible = swipeableState.currentValue == SharedScreen.Details,
        enter = fadeIn(tween(200)) + slideInVertically(initialOffsetY = { it }),
        exit = fadeOut(tween()) + slideOutVertically(targetOffsetY = { it }),
    ) {
        WonderDetailsScreen(
            wonder = currentWonder,
            navigateToTimeline = {
                openTimelineScreen(currentWonder)
            },
            openArtifactDetailsScreen = openArtifactDetailsScreen,
            onPressHome = {
                scope.launch {
                    swipeableState.animateTo(SharedScreen.Home)
                }
            },
            openMapScreen = openMapScreen,
            openArtifactsScreen = { openArtifactListScreen(currentWonder) },
            openVideoScreen = openVideoScreen
        )
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

}