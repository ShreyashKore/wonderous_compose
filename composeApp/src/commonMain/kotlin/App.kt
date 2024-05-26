import androidx.compose.animation.slideIn
import androidx.compose.animation.slideOut
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.IntOffset
import androidx.lifecycle.viewmodel.compose.viewModel
import models.Wonder
import models.parse
import moe.tlaster.precompose.PreComposeApp
import moe.tlaster.precompose.flow.collectAsStateWithLifecycle
import moe.tlaster.precompose.navigation.NavHost
import moe.tlaster.precompose.navigation.path
import moe.tlaster.precompose.navigation.query
import moe.tlaster.precompose.navigation.rememberNavigator
import moe.tlaster.precompose.navigation.transition.NavTransition
import ui.screens.ArtifactDetailsScreen
import ui.screens.ArtifactListScreen
import ui.screens.ArtifactListViewModel
import ui.screens.MapScreen
import ui.screens.SharedAnimationContainer
import ui.screens.YoutubeVideoScreen
import ui.screens.timeline.TimelineScreen
import ui.theme.ColorScheme
import ui.theme.Typography

@Composable
fun App() {
    MaterialTheme(
        colorScheme = ColorScheme,
        typography = Typography,
    ) {
        PreComposeApp {
            val navigator = rememberNavigator()
            NavHost(
                navigator = navigator,
                initialRoute = "/wonder/chichenitza"
            ) {
                scene("/wonder/{type}") { backStackEntry ->
                    val id = backStackEntry.path<String>("type")
                    val wonder = Wonder.parse(id)
                    SharedAnimationContainer(
                        initialWonder = wonder,
                        openTimelineScreen = { navigator.navigate("/timeline?type=${it?.title}") },
                        openArtifactDetailsScreen = { navigator.navigate("/artifact/${it}") },
                        openArtifactListScreen = { navigator.navigate("/search/${it.title}") },
                        openMapScreen = { navigator.navigate("/maps/${it.title}") },
                        openVideoScreen = { navigator.navigate("/video/$it") },
                    )
                }
                scene(
                    "/timeline",
                    navTransition = DefaultNavTransition
                ) { backStackEntry ->
                    val id = backStackEntry.query<String>("type")
                    val wonder = Wonder.parse(id)
                    TimelineScreen(
                        selectedWonder = wonder,
                        onClickBack = { navigator.goBack() },
                    )
                }

                scene(
                    "/artifact/{id}",
                    navTransition = DefaultNavTransition
                ) { backStackEntry ->
                    val id = backStackEntry.path<String>("id")!!
                    ArtifactDetailsScreen(
                        artifactId = id,
                        onClickBack = { navigator.goBack() },
                    )
                }

                scene(
                    "/search/{type}",
                    navTransition = DefaultNavTransition
                ) { backStackEntry ->
                    val id = backStackEntry.path<String>("type")
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
                        onBackClick = { navigator.goBack() },
                    )
                }

                scene(
                    "/maps/{type}",
                    navTransition = DefaultNavTransition
                ) { backStackEntry ->
                    val id = backStackEntry.path<String>("type")
                    val wonder = Wonder.parse(id)
                    MapScreen(
                        latLng = wonder.latLng,
                        onBackClick = { navigator.goBack() }
                    )
                }

                scene(
                    "/video/{id}",
                    navTransition = DefaultNavTransition
                ) { backStackEntry ->
                    val id = backStackEntry.path<String>("id")!!
                    YoutubeVideoScreen(
                        id = id,
                        onBackClick = { navigator.goBack() }
                    )
                }
            }
        }
    }
}

private val DefaultNavTransition = NavTransition(
    createTransition = slideIn { IntOffset(it.width, 0) },
    destroyTransition = slideOut { IntOffset(it.width, 0) },
)