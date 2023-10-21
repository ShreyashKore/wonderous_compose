import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import ui.screens.SharedAnimationContainer
import ui.theme.Typography

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun App() {
    MaterialTheme(
        typography = Typography
    ) {
        Column(
            Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            SharedAnimationContainer()
        }
    }
}


expect fun getPlatformName(): String