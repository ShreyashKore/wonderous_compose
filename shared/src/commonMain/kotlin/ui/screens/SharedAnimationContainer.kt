package ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import models.ChichenItza
import models.Wonder
import models.Wonders
import org.jetbrains.compose.resources.ExperimentalResourceApi
import ui.AppIcons
import ui.composables.AppIconButton
import ui.theme.greyStrong


enum class SharedScreen {
    Home, Details
}

@OptIn(
    ExperimentalAnimationApi::class,
    ExperimentalFoundationApi::class,
    ExperimentalMaterialApi::class, ExperimentalResourceApi::class,
)
@Composable
fun SharedAnimationContainer(
    initialWonder: Wonder = ChichenItza,
    openTimelineScreen: (wonder: Wonder?) -> Unit,
    openHomeScreen: Boolean = true,
) = BoxWithConstraints {

    var isMenuOpen by rememberSaveable { mutableStateOf(false) }
    val pagerState = rememberPagerState(initialPage = Wonders.indexOf(initialWonder) * 100_000,
        pageCount = { Int.MAX_VALUE })
    val currentWonder = Wonders[pagerState.currentPage % Wonders.size]

    val swipeableState = rememberSwipeableState(SharedScreen.Home)
    println("PagerState ${pagerState.currentPage} ${pagerState.settledPage}")
    val scope = rememberCoroutineScope()


    HomeScreen(
        currentWonder = currentWonder,
        pagerState = pagerState,
        swipeableState = swipeableState,
        openDetailScreen = {
            scope.launch {
                swipeableState.animateTo(SharedScreen.Details)
            }
        }
    )

    AnimatedVisibility(
        !isMenuOpen,
        enter = fadeIn(),
        exit = fadeOut()
    ) {
        Box(
            Modifier.fillMaxSize()
                .height(72.dp)
                .padding(horizontal = 8.dp, vertical = 8.dp)
                .align(Alignment.TopCenter)
        ) {
            AppIconButton(
                iconPath = AppIcons.Menu,
                contentDescription = "Options",
                onClick = { isMenuOpen = true }
            )
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
            onPressHome = {
                scope.launch {
                    swipeableState.animateTo(SharedScreen.Home)
                }
            },
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
            modifier = Modifier.fillMaxSize().background(greyStrong.copy(.8f)),
            openTimeline = { openTimelineScreen(currentWonder) },
            openCollection = {}
        )
    }

}