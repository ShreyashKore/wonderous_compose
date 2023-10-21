package ui.screens

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.with
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

    AnimatedContent(
        targetState = swipeableState.currentValue,
        transitionSpec = {
            fadeIn(tween(200)) + slideIntoContainer(AnimatedContentScope.SlideDirection.Up) with
                    fadeOut(tween()) + slideOutOfContainer(AnimatedContentScope.SlideDirection.Down)
        },
    ) {
        when (it) {
            SharedScreen.Home -> HomeScreen(
                currentWonder = currentWonder,
                pagerState = pagerState,
                swipeableState = swipeableState,
                openDetailScreen = {
                    scope.launch {
                        swipeableState.animateTo(SharedScreen.Details)
                    }
                }
            )

            SharedScreen.Details -> WonderDetailsScreen(
                wonder = currentWonder,
                onPressHome = {
                    scope.launch {
                        swipeableState.animateTo(SharedScreen.Home)
                    }
                },
            )
        }
    }
}