package ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.rememberSwipeableState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.launch
import models.ChichenItza
import models.Wonder
import models.Wonders


enum class SharedScreen {
    Home, Details
}

@OptIn(
    ExperimentalAnimationApi::class,
    ExperimentalFoundationApi::class,
    ExperimentalMaterialApi::class,
)
@Composable
fun SharedAnimationContainer(
    initialWonder: Wonder = ChichenItza,
    openHomeScreen: Boolean = true
) = BoxWithConstraints {
    val pagerState = rememberPagerState(initialPage = Wonders.indexOf(initialWonder) * 100_000)
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
        visible = swipeableState.currentValue == SharedScreen.Details,
        enter = fadeIn(tween(200)) + slideInVertically(initialOffsetY = { it }),
        exit = fadeOut(tween()) + slideOutVertically(targetOffsetY = { it }),
    ) {
        WonderDetailsScreen(
            wonder = currentWonder,
            onPressHome = {
                scope.launch {
                    swipeableState.animateTo(SharedScreen.Home)
                }
            },
        )
    }
}