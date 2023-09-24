package ui.screens

import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import models.Wonder

@Composable
fun WonderEvents(
    wonder: Wonder,
) {
    Text(
        "WonderEvents",
        style = MaterialTheme.typography.displayLarge
    )
}