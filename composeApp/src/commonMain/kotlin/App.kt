import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import models.Wonder
import models.parse
import ui.screens.ArtifactDetailsScreen
import ui.screens.ArtifactListScreen
import ui.screens.ArtifactListViewModel
import ui.screens.MapScreen
import ui.screens.WonderDetailsScreen
import ui.screens.YoutubeVideoScreen
import ui.screens.home.HomeScreen
import ui.screens.timeline.TimelineScreen
import ui.theme.ColorScheme
import ui.theme.Typography

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun App() {
    MaterialTheme(
        colorScheme = ColorScheme,
        typography = Typography,
    ) {
        val navigator = rememberNavController()

        SharedTransitionLayout {

            NavHost(
                navController = navigator,
                startDestination = AppRoute.Home,
                enterTransition = { DefaultEnterTransition },
                exitTransition = { DefaultExitTransition },
                popEnterTransition = { DefaultPopEnterTransition },
                popExitTransition = { DefaultPopExitTransition },
            ) {
                composable<AppRoute.Home> { backStackEntry ->
                    // Temporary fix as current Wonder is not being remembered when in backstack
                    val savedStateHandle = backStackEntry.savedStateHandle
                    val currentWonderName = savedStateHandle.get<String>("currentWonder")
                    val currentWonder = Wonder.parse(currentWonderName)
                    HomeScreen(
                        initialWonder = currentWonder,
                        openDetailScreen = {
                            navigator.navigate(AppRoute.Wonder(it.name))
                        },
                        openTimelineScreen = {
                            navigator.navigate(AppRoute.Timeline(it.name))
                        },
                        animatedVisibilityScope = this,
                        sharedTransitionScope = this@SharedTransitionLayout,
                        onChangeCurrentWonder = {
                            savedStateHandle["currentWonder"] = it.name
                        }
                    )

                }

                composable<AppRoute.Wonder> { backStackEntry ->
                    val route = backStackEntry.toRoute<AppRoute.Wonder>()
                    val wonder = Wonder.parse(route.wonderId)
                    WonderDetailsScreen(
                        wonder = wonder,
                        onPressHome = { navigator.popBackStack() },
                        openTimelineScreen = { navigator.navigate(AppRoute.Timeline(it?.name)) },
                        openArtifactDetailsScreen = { navigator.navigate(AppRoute.ArtifactDetails(it)) },
                        openArtifactListScreen = { navigator.navigate(AppRoute.Search(it.name)) },
                        openMapScreen = { navigator.navigate(AppRoute.Maps(it.name)) },
                        openVideoScreen = { navigator.navigate(AppRoute.Video(it)) },
                        animatedVisibilityScope = this,
                        sharedTransitionScope = this@SharedTransitionLayout,
                    )
                }

                composable<AppRoute.Timeline> { backStackEntry ->
                    val route = backStackEntry.toRoute<AppRoute.Timeline>()
                    val wonder = Wonder.parse(route.wonderId)
                    TimelineScreen(
                        selectedWonder = wonder,
                        onClickBack = { navigator.popBackStack() },
                    )
                }

                composable<AppRoute.ArtifactDetails> { backStackEntry ->
                    val route = backStackEntry.toRoute<AppRoute.ArtifactDetails>()
                    ArtifactDetailsScreen(
                        artifactId = route.artifactId,
                        onClickBack = { navigator.popBackStack() },
                    )
                }

                composable<AppRoute.Search> { backStackEntry ->
                    val route = backStackEntry.toRoute<AppRoute.Search>()
                    val wonder = Wonder.parse(route.wonderId)
                    val viewModel = viewModel { ArtifactListViewModel(wonder) }
                    ArtifactListScreen(
                        wonder = wonder,
                        searchText = viewModel.searchText.collectAsStateWithLifecycle().value,
                        onSearch = viewModel::onSearch,
                        onQueryChange = viewModel::onQueryChange,
                        suggestions = viewModel.suggestions.collectAsStateWithLifecycle().value,
                        filteredArtifacts = viewModel.filteredArtifacts.collectAsStateWithLifecycle().value,
                        onClickArtifact = { navigator.navigate(AppRoute.ArtifactDetails("$it")) },
                        onBackClick = { navigator.navigateUp() },
                    )
                }

                composable<AppRoute.Maps> { backStackEntry ->
                    val route = backStackEntry.toRoute<AppRoute.Maps>()
                    val wonder = Wonder.parse(route.wonderId)
                    MapScreen(
                        latLng = wonder.latLng,
                        onBackClick = { navigator.popBackStack() }
                    )
                }

                composable<AppRoute.Video> { backStackEntry ->
                    val route = backStackEntry.toRoute<AppRoute.Video>()
                    YoutubeVideoScreen(
                        id = route.videoId,
                        onBackClick = { navigator.popBackStack() }
                    )
                }
            }
        }
    }
}

private val DefaultEnterTransition = fadeIn(tween(500))

val AnimatedContentTransitionScope<NavBackStackEntry>.DefaultExitTransition
    get() = fadeOut(tween(600, delayMillis = 100)) +
            ExitTransition.KeepUntilTransitionsFinished

private val DefaultPopEnterTransition = fadeIn(tween(200))

val AnimatedContentTransitionScope<NavBackStackEntry>.DefaultPopExitTransition
    get() = fadeOut(tween(500)) + ExitTransition.KeepUntilTransitionsFinished
