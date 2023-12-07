package ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.rememberSwipeableState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.movableContentWithReceiverOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.skydoves.orbital.Orbital
import com.skydoves.orbital.OrbitalScope
import com.skydoves.orbital.animateSharedElementTransition
import kotlinx.coroutines.launch
import models.ChichenItza
import models.Wonder
import models.Wonders
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import ui.AppIcons
import ui.composables.AppIconButton
import ui.getAssetPath
import ui.mainImageName
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
    openArtifactDetailsScreen: (id: String) -> Unit,
    openArtifactListScreen: (wonder: Wonder) -> Unit,
    openMapScreen: (wonder: Wonder) -> Unit,
    openVideoScreen: (videoId: String) -> Unit,
    openHomeScreen: Boolean = true,
) = BoxWithConstraints {

    var isMenuOpen by rememberSaveable { mutableStateOf(false) }
    val pagerState = rememberPagerState(initialPage = Wonders.indexOf(initialWonder) * 100_000,
        pageCount = { Int.MAX_VALUE })
    val wonder = Wonders[pagerState.currentPage % Wonders.size]

    val swipeableState = rememberSwipeableState(SharedScreen.Home)
    val isHomePage = swipeableState.currentValue == SharedScreen.Home
    val scope = rememberCoroutineScope()
    val sharedElement = rememberContentWithOrbitalScope(wonder) {
        Image(
            painterResource(wonder.getAssetPath(wonder.mainImageName)),
            contentDescription = "main",
            modifier = Modifier
                .graphicsLayer {
//                val scale = 1 - swipeProgress * .01f
//                scaleX = scale
//                scaleY = scale
                }
                .run {
                    (
                            if (isHomePage)
                                wrapContentSize(
                                    unbounded = true
                                ).requiredHeight(maxHeight * wonder.fractionalScale)
                            else fillMaxWidth()
                                .height(280.dp)
                                .zIndex(0.1f)
                            )

                        .animateSharedElementTransition(
                            this@rememberContentWithOrbitalScope,
                            movementSpec = tween(1000),
                            transformSpec = tween(1000)
                        )
                },
            contentScale = ContentScale.FillHeight,
        )
    }


    val alpha = remember {
        androidx.compose.animation.core.Animatable(0f)
    }
    LaunchedEffect(isHomePage) {
        alpha.animateTo(if (isHomePage) 0f else 1f, tween(2000))
    }

    Orbital {
        HomeScreen(
            currentWonder = wonder,
            pagerState = pagerState,
            swipeableState = swipeableState,
            openDetailScreen = {
                scope.launch {
                    swipeableState.animateTo(SharedScreen.Details)
                }
            }
        ) {
        }

        if (isHomePage)
            Box(Modifier.alpha(1f - alpha.value)) {
                HomeScreen(
                    currentWonder = wonder,
                    pagerState = pagerState,
                    swipeableState = swipeableState,
                    openDetailScreen = {
                        scope.launch {
                            swipeableState.animateTo(SharedScreen.Details)
                        }
                    }
                ) {
                    sharedElement()
                }
            }
        else
            Box(Modifier.alpha(alpha.value)) {
                WonderDetailsScreen(
                    wonder = wonder,
                    navigateToTimeline = {
                        openTimelineScreen(wonder)
                    },
                    openArtifactDetailsScreen = openArtifactDetailsScreen,
                    onPressHome = {
                        scope.launch {
                            swipeableState.animateTo(SharedScreen.Home)
                        }
                    },
                    openMapScreen = openMapScreen,
                    openArtifactsScreen = { openArtifactListScreen(wonder) },
                    openVideoScreen = openVideoScreen
                ) {
                    sharedElement()
                }
            }

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
            isMenuOpen,
            enter = fadeIn(),
            exit = fadeOut(),
        ) {
            HomeMenu(
                wonder,
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
                openTimeline = { openTimelineScreen(wonder) },
                openCollection = {}
            )
        }
    }

}

@Composable
fun rememberContentWithOrbitalScope(
    key1: Any?, content: @Composable OrbitalScope.() -> Unit,
): @Composable OrbitalScope.() -> Unit {
    return remember(key1) {
        movableContentWithReceiverOf(content = content)
    }
}
