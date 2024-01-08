package ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.rememberSwipeableState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import models.GreatWall
import models.Wonder
import models.Wonders
import ui.AppIcons
import ui.composables.AppIconButton
import ui.composables.GithubButton
import ui.screens.home.HomeMenu
import ui.screens.home.HomeScreen
import ui.theme.greyStrong


enum class SharedScreen {
    Home, Details
}

@OptIn(
    ExperimentalFoundationApi::class,
    ExperimentalMaterialApi::class,
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
        rememberPagerState(initialPage = 100_000 * Wonders.size + Wonders.indexOf(initialWonder),
            pageCount = { Int.MAX_VALUE })
    val currentWonder = Wonders[pagerState.currentPage % Wonders.size]

    val swipeableState = rememberSwipeableState(SharedScreen.Home)
    val scope = rememberCoroutineScope()


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
                iconPath = AppIcons.Menu,
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