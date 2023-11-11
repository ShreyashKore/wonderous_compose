import androidx.compose.animation.slideIn
import androidx.compose.animation.slideOut
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.IntOffset
import models.Wonder
import models.parse
import moe.tlaster.precompose.PreComposeApp
import moe.tlaster.precompose.navigation.NavHost
import moe.tlaster.precompose.navigation.path
import moe.tlaster.precompose.navigation.query
import moe.tlaster.precompose.navigation.rememberNavigator
import moe.tlaster.precompose.navigation.transition.NavTransition
import ui.screens.ArtifactDetailsScreen
import ui.screens.SharedAnimationContainer
import ui.screens.TimeLineScreen
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
                        openArtifactDetailsScreen = { navigator.navigate("/artifact/${it}") }
                    )
                }
                scene(
                    "/timeline",
                    navTransition = NavTransition(
                        createTransition = slideIn { IntOffset(it.width, 0) },
                        destroyTransition = slideOut { IntOffset(it.width, 0) },
                    )
                ) { backStackEntry ->
                    val id = backStackEntry.query<String>("type")
                    val wonder = Wonder.parse(id)
                    TimeLineScreen(
                        selectedWonder = wonder,
                        onClickBack = {
                            navigator.goBack()
                        },
                    )
                }

                scene(
                    "/artifact/{id}",
                    navTransition = NavTransition(
                        createTransition = slideIn { IntOffset(it.width, 0) },
                        destroyTransition = slideOut { IntOffset(it.width, 0) },
                    )
                ) { backStackEntry ->
                    val id = backStackEntry.path<String>("id")!!
                    ArtifactDetailsScreen(
                        artifactId = id,
                        onClickBack = { navigator.goBack() },
                    )
                }
            }

        }
    }
}


expect fun getPlatformName(): String