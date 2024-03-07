package ui.theme


import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.Font
import wonderouscompose.composeapp.generated.resources.B612Mono_Regular
import wonderouscompose.composeapp.generated.resources.CinzelDecorative_Black
import wonderouscompose.composeapp.generated.resources.CinzelDecorative_Bold
import wonderouscompose.composeapp.generated.resources.CinzelDecorative_Regular
import wonderouscompose.composeapp.generated.resources.MaShanZheng_Regular
import wonderouscompose.composeapp.generated.resources.Raleway_Bold
import wonderouscompose.composeapp.generated.resources.Raleway_BoldItalic
import wonderouscompose.composeapp.generated.resources.Raleway_ExtraBold
import wonderouscompose.composeapp.generated.resources.Raleway_ExtraBoldItalic
import wonderouscompose.composeapp.generated.resources.Raleway_Italic
import wonderouscompose.composeapp.generated.resources.Raleway_Medium
import wonderouscompose.composeapp.generated.resources.Raleway_MediumItalic
import wonderouscompose.composeapp.generated.resources.Raleway_Regular
import wonderouscompose.composeapp.generated.resources.Res
import wonderouscompose.composeapp.generated.resources.TenorSans_Regular
import wonderouscompose.composeapp.generated.resources.YesevaOne_Regular

@OptIn(ExperimentalResourceApi::class)
val Cinzel
    @Composable get() = FontFamily(
        Font(Res.font.CinzelDecorative_Regular, FontWeight.Normal, FontStyle.Normal),
        Font(Res.font.CinzelDecorative_Bold, FontWeight.Bold, FontStyle.Normal),
        Font(Res.font.CinzelDecorative_Black, FontWeight.Black, FontStyle.Normal),
    )

@OptIn(ExperimentalResourceApi::class)
val Raleway
    @Composable get() = FontFamily(
        Font(Res.font.Raleway_Regular, FontWeight.Normal, FontStyle.Normal),
        Font(Res.font.Raleway_Italic, FontWeight.Normal, FontStyle.Italic),
        Font(Res.font.Raleway_Medium, FontWeight.Medium, FontStyle.Normal),
        Font(Res.font.Raleway_MediumItalic, FontWeight.Medium, FontStyle.Italic),
        Font(Res.font.Raleway_ExtraBold, FontWeight.ExtraBold, FontStyle.Normal),
        Font(Res.font.Raleway_ExtraBoldItalic, FontWeight.ExtraBold, FontStyle.Italic),
        Font(Res.font.Raleway_Bold, FontWeight.Bold, FontStyle.Normal),
        Font(Res.font.Raleway_BoldItalic, FontWeight.Bold, FontStyle.Italic),
    )

@OptIn(ExperimentalResourceApi::class)
val MaShanZheng
    @Composable get() = FontFamily(
        Font(Res.font.MaShanZheng_Regular, FontWeight.Normal, FontStyle.Normal),
    )

@OptIn(ExperimentalResourceApi::class)
val B612Mono
    @Composable get() = FontFamily(
        Font(Res.font.B612Mono_Regular, FontWeight.Normal, FontStyle.Normal),
    )

@OptIn(ExperimentalResourceApi::class)
val TenorSans
    @Composable get() = FontFamily(
        Font(Res.font.TenorSans_Regular, FontWeight.Normal, FontStyle.Normal),
    )

@OptIn(ExperimentalResourceApi::class)
val YesevaOne
    @Composable get() = FontFamily(
        Font(Res.font.YesevaOne_Regular, FontWeight.Normal, FontStyle.Normal),
    )

val Typography
    @Composable get() = Typography(
        displayLarge = TextStyle(fontFamily = Raleway, fontSize = 72.sp),
        displayMedium = TextStyle(fontFamily = Raleway, fontSize = 62.sp),
        displaySmall = TextStyle(fontFamily = Cinzel, fontSize = 56.sp),
        titleLarge = TextStyle(fontFamily = YesevaOne, fontSize = 52.sp),
        titleMedium = TextStyle(fontFamily = YesevaOne, fontSize = 28.sp),
        titleSmall = TextStyle(fontFamily = TenorSans, fontSize = 18.sp),
        bodyLarge = TextStyle(fontFamily = Raleway, fontSize = 18.sp),
        bodyMedium = TextStyle(fontFamily = Raleway),
        bodySmall = TextStyle(fontFamily = Raleway),
    )

val Typography.quoteFont: TextStyle
    @Composable get() = TextStyle(fontFamily = Cinzel)


fun FontFamily(vararg fonts: Font?) =
    if (fonts.all { it == null }) FontFamily.Default else FontFamily(fonts.toList().filterNotNull())




