package dev.shreyash.wonderouscompose.ui.screens

import androidx.compose.runtime.Composable

@Composable
expect fun YoutubeVideoScreen(
    id: String,
    onBackClick: () -> Unit,
)