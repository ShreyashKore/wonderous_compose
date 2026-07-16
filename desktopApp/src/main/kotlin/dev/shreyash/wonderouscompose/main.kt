package dev.shreyash.wonderouscompose

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "Wonderous Compose",
    ) {
        App()
    }
}