package ui.screens

//import com.multiplatform.webview.web.WebView
//import com.multiplatform.webview.web.rememberWebViewState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import ui.composables.BackButton
import ui.theme.black

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun YoutubeVideoScreen(
    id: String,
    onBackClick: () -> Unit,
) {
    Box(
        Modifier.background(black)
    ) {
//        val webViewState = rememberWebViewState("https://www.youtube.com/embed/$id")
//        WebView(
//            state = webViewState,
//            modifier = Modifier.fillMaxSize()
//        )

        TopAppBar(
            title = { },
            navigationIcon = {
                BackButton(onClick = onBackClick)
            },
            colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent),
            modifier = Modifier.align(Alignment.TopCenter)
        )
    }
}