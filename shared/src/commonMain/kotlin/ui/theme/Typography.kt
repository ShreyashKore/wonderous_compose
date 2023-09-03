package ui.theme

import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import platform.font

val Cinzel
    @Composable get() = FontFamily(
        font("Cinzel", "CinzelDecorative-Regular", FontWeight.Normal, FontStyle.Normal),
        font("Cinzel", "CinzelDecorative-Bold", FontWeight.Bold, FontStyle.Normal),
        font("Cinzel", "CinzelDecorative-Black", FontWeight.Black, FontStyle.Normal),
    )

val Raleway
    @Composable get() = FontFamily(
        font("Raleway", "Raleway-Regular", FontWeight.Normal, FontStyle.Normal),
        font("Raleway", "Raleway-Italic", FontWeight.Normal, FontStyle.Italic),
        font("Raleway", "Raleway-Medium", FontWeight.Medium, FontStyle.Normal),
        font("Raleway", "Raleway-Mediumitalic", FontWeight.Medium, FontStyle.Italic),
        font("Raleway", "Raleway-Extrabold", FontWeight.ExtraBold, FontStyle.Normal),
        font("Raleway", "Raleway-Extrabolditalic", FontWeight.ExtraBold, FontStyle.Italic),
        font("Raleway", "Raleway-Bold", FontWeight.Bold, FontStyle.Normal),
        font("Raleway", "Raleway-Bolditalic", FontWeight.Bold, FontStyle.Italic),
    )

val MaShanZheng
    @Composable get() = FontFamily(
        font("MaShanZheng", "MaShanZheng-Regular", FontWeight.Normal, FontStyle.Normal),
    )
val B612Mono
    @Composable get() = FontFamily(
        font("B612Mono", "B612Mono-Regular", FontWeight.Normal, FontStyle.Normal),
    )
val TenorSans
    @Composable get() = FontFamily(
        font("TenorSans", "TenorSans-Regular", FontWeight.Normal, FontStyle.Normal),
    )
val YesevaOne
    @Composable get() = FontFamily(
        font("YesevaOne", "YesevaOne-Regular", FontWeight.Normal, FontStyle.Normal),
    )

val Typography
    @Composable get() = Typography(
        displayLarge = TextStyle(fontFamily = Raleway, fontSize = 72.sp),
        displayMedium = TextStyle(fontFamily = Raleway, fontSize = 56.sp),
        titleLarge = TextStyle(fontFamily = YesevaOne, fontSize = 52.sp),
        titleMedium = TextStyle(fontFamily = YesevaOne, fontSize = 28.sp),
        titleSmall = TextStyle(fontFamily = TenorSans, fontSize = 18.sp),
        bodyLarge = TextStyle(fontFamily = Raleway, fontSize = 18.sp),
        bodyMedium = TextStyle(fontFamily = Raleway),
        bodySmall = TextStyle(fontFamily = Raleway),
    )

val Typography.quoteFont: TextStyle
    @Composable get() = TextStyle(fontFamily = Cinzel)






