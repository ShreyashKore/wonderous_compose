package ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
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
actual fun YoutubeVideoScreen(id: String, onBackClick: () -> Unit) {
    Box(
        Modifier.background(black)
    ) {
        Text("WebView not implemented")


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