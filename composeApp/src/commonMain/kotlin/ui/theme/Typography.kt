package ui.theme


import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.unit.sp


val displayLargeFont: TextStyle
    @Composable get() = when (Locale.current.language) {
        "en" -> TextStyle(fontFamily = Raleway, fontSize = 72.sp)
        "hi" -> TextStyle(fontFamily = NotoSans, fontSize = 72.sp)
        else -> default
    }

val displayMediumFont: TextStyle
    @Composable get() = when (Locale.current.language) {
        "en" -> TextStyle(fontFamily = Raleway, fontSize = 62.sp)
        "hi" -> TextStyle(fontFamily = NotoSans, fontSize = 62.sp)
        else -> default
    }


val displaySmallFont: TextStyle
    @Composable get() = when (Locale.current.language) {
        "en" -> TextStyle(fontFamily = Raleway, fontSize = 56.sp)
        "hi" -> TextStyle(fontFamily = NotoSans, fontSize = 56.sp)
        else -> default
    }

val titleLargeFont: TextStyle
    @Composable get() = when (Locale.current.language) {
        "en" -> TextStyle(fontFamily = YesevaOne, fontSize = 52.sp)
        "hi" -> TextStyle(fontFamily = Gotu, fontSize = 42.sp, fontWeight = FontWeight.Bold)
        else -> default
    }

val titleMediumFont: TextStyle
    @Composable get() = when (Locale.current.language) {
        "en" -> TextStyle(fontFamily = YesevaOne, fontSize = 28.sp)
        "hi" -> TextStyle(fontFamily = NotoSans, fontSize = 26.sp)
        else -> default
    }


val titleSmallFont: TextStyle
    @Composable get() = when (Locale.current.language) {
        "en" -> TextStyle(fontFamily = TenorSans, fontSize = 18.sp)
        "hi" -> TextStyle(fontFamily = NotoSans, fontSize = 18.sp)
        else -> default
    }

val bodyLarge: TextStyle
    @Composable get() = when (Locale.current.language) {
        "en" -> TextStyle(fontFamily = Raleway, fontSize = 18.sp)
        "hi" -> TextStyle(fontFamily = NotoSans, fontSize = 18.sp)
        else -> default
    }

val bodyMedium: TextStyle
    @Composable get() = when (Locale.current.language) {
        "en" -> TextStyle(fontFamily = Raleway)
        "hi" -> TextStyle(fontFamily = NotoSans)
        else -> default
    }

val bodySmall: TextStyle
    @Composable get() = when (Locale.current.language) {
        "en" -> TextStyle(fontFamily = Raleway)
        "hi" -> TextStyle(fontFamily = NotoSans)
        else -> default
    }

val default: TextStyle
    @Composable get() = when (Locale.current.language) {
        "zh" -> TextStyle(fontFamily = NotoSansSc)
        else -> TextStyle(fontFamily = NotoSans)
    }

val Typography
    @Composable get() = Typography(
        displayLarge = displayLargeFont,
        displayMedium = displayMediumFont,
        displaySmall = displaySmallFont,
        titleLarge = titleLargeFont,
        titleMedium = titleMediumFont,
        titleSmall = titleSmallFont,
        bodyLarge = bodyLarge,
        bodyMedium = bodyMedium,
        bodySmall = bodySmall,
        labelLarge = default.copy(fontSize = 13.sp),
        labelMedium = default.copy(fontSize = 11.sp),
        labelSmall = default.copy(fontSize = 9.sp),
    )

val Typography.quoteFont: TextStyle
    @Composable get() = TextStyle(
        fontFamily = when (Locale.current.language) {
            "en" -> Cinzel
            "zh" -> MaShanZheng
            "hi" -> Gotu
            else -> defaultFontFamily
        },
        fontWeight = FontWeight.W600
    )

