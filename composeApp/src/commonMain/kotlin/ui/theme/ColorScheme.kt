package ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color


val accent1 = Color(0xFFE4935D)
val accent2 = Color(0xFFBEABA1)
val offWhite = Color(0xFFF8ECE5)
val caption = Color(0xFF7D7873)
val body = Color(0xFF514F4D)
val greyStrong = Color(0xFF272625)
val greyMedium = Color(0xFF9D9995)
val white = Color.White
val black = Color(0xFF1E1B18)

val isDark = false
val txtColor = Color.Black

val ColorScheme
    @Composable get() = MaterialTheme.colorScheme.copy(
        primary = accent1,
        primaryContainer = accent1,
        secondary = accent2,
        secondaryContainer = accent2,
        background = offWhite,
        surface = offWhite,
        onBackground = txtColor,
        onSurface = txtColor,
        onError = Color.White,
        onPrimary = Color.White,
        onSecondary = Color.White,
        error = Color.Red.copy(alpha = 0.4f) // Adjust the alpha value as needed
    )