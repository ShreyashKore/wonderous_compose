import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import models.Wonder
import models.parse
import moe.tlaster.precompose.PreComposeApp
import moe.tlaster.precompose.navigation.NavHost
import moe.tlaster.precompose.navigation.path
import moe.tlaster.precompose.navigation.query
import moe.tlaster.precompose.navigation.rememberNavigator
import ui.screens.SharedAnimationContainer
import ui.screens.TimeLineScreen
import ui.theme.ColorScheme
import ui.theme.Typography

@Composable
fun App() {
    MaterialTheme(
        colorScheme = ColorScheme,
        typography = Typography
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
                    )
                }
                scene("/timeline") { backStackEntry ->
                    val id = backStackEntry.query<String>("type")
                    val wonder = Wonder.parse(id)
                    TimeLineScreen(
                        selectedWonder = wonder,
                        onClickBack = {
                            navigator.goBack()
                        },
                    )
                }
            }

        }
    }
}


expect fun getPlatformName(): String