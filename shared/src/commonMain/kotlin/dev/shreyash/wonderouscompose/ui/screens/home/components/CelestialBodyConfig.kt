package ui.screens.home.components

import androidx.compose.ui.Alignment


data class CelestialBodyConfig(
    val name: String,
    val alignment: Alignment,
    val height: Float,
    val hiddenStateYOffset: Float,
)
