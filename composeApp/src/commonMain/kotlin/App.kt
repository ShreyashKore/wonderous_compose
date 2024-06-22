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
                startDestination = "/home",
                enterTransition = { DefaultEnterTransition },
                exitTransition = { DefaultExitTransition },
                popEnterTransition = { DefaultPopEnterTransition },
                popExitTransition = { DefaultPopExitTransition },
            ) {
                composable(
                    "/home",
                ) { backStackEntry ->
                    // Temporary fix as current Wonder is not being remembered when in backstack
                    val savedStateHandle = backStackEntry.savedStateHandle
                    val currentWonderName = savedStateHandle.get<String>("currentWonder")
                    val currentWonder = Wonder.parse(currentWonderName)
                    HomeScreen(
                        initialWonder = currentWonder,
                        openDetailScreen = {
                            navigator.navigate("/home/wonder/${it.title}")
                        },
                        openTimelineScreen = {
                            navigator.navigate("/timeline?type=${it.title}")
                        },
                        animatedVisibilityScope = this,
                        sharedTransitionScope = this@SharedTransitionLayout,
                        onChangeCurrentWonder = {
                            savedStateHandle["currentWonder"] = it.title
                        }
                    )

                }

                composable(
                    "/home/wonder/{type}",
                ) { backStackEntry ->
                    val id = backStackEntry.arguments?.getString("type")
                    val wonder = Wonder.parse(id)
                    WonderDetailsScreen(
                        wonder = wonder,
                        onPressHome = { navigator.popBackStack() },
                        openTimelineScreen = { navigator.navigate("/timeline?type=${it?.title}") },
                        openArtifactDetailsScreen = { navigator.navigate("/artifact/${it}") },
                        openArtifactListScreen = { navigator.navigate("/search/${it.title}") },
                        openMapScreen = { navigator.navigate("/maps/${it.title}") },
                        openVideoScreen = { navigator.navigate("/video/$it") },
                        animatedVisibilityScope = this,
                        sharedTransitionScope = this@SharedTransitionLayout,
                    )
                }
                composable(
                    "/timeline",
                ) { backStackEntry ->
                    val id = backStackEntry.arguments?.getString("type")
                    val wonder = Wonder.parse(id)
                    TimelineScreen(
                        selectedWonder = wonder,
                        onClickBack = { navigator.popBackStack() },
                    )
                }

                composable(
                    "/artifact/{id}",
                ) { backStackEntry ->
                    val id = backStackEntry.arguments?.getString("id")!!
                    ArtifactDetailsScreen(
                        artifactId = id,
                        onClickBack = { navigator.popBackStack() },
                    )
                }

                composable(
                    "/search/{type}",
                ) { backStackEntry ->
                    val id = backStackEntry.arguments?.getString("type")
                    val wonder = Wonder.parse(id)
                    val viewModel = viewModel { ArtifactListViewModel(wonder) }
                    ArtifactListScreen(
                        wonder = wonder,
                        searchText = viewModel.searchText.collectAsStateWithLifecycle().value,
                        onSearch = viewModel::onSearch,
                        onQueryChange = viewModel::onQueryChange,
                        suggestions = viewModel.suggestions.collectAsStateWithLifecycle().value,
                        filteredArtifacts = viewModel.filteredArtifacts.collectAsStateWithLifecycle().value,
                        onClickArtifact = { navigator.navigate("/artifact/${it}") },
                        onBackClick = { navigator.navigateUp() },
                    )
                }

                composable(
                    "/maps/{type}",
                ) { backStackEntry ->
                    val id = backStackEntry.arguments?.getString("type")
                    val wonder = Wonder.parse(id)
                    MapScreen(
                        latLng = wonder.latLng,
                        onBackClick = { navigator.popBackStack() }
                    )
                }

                composable(
                    "/video/{id}",
                ) { backStackEntry ->
                    val id = backStackEntry.arguments?.getString("id")!!
                    YoutubeVideoScreen(
                        id = id,
                        onBackClick = { navigator.popBackStack() }
                    )
                }
            }
        }
    }
}

private val DefaultEnterTransition
    get() = fadeIn(tween(500))

val AnimatedContentTransitionScope<NavBackStackEntry>.DefaultExitTransition
    get() = fadeOut(tween(600, delayMillis = 100)) +
            ExitTransition.KeepUntilTransitionsFinished

private val DefaultPopEnterTransition
    get() = fadeIn(tween(200))

val AnimatedContentTransitionScope<NavBackStackEntry>.DefaultPopExitTransition
    get() = fadeOut(tween(500)) + ExitTransition.KeepUntilTransitionsFinished
