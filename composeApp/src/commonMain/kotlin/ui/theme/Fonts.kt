package ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import org.jetbrains.compose.resources.Font
import wonderouscompose.composeapp.generated.resources.B612Mono_Regular
import wonderouscompose.composeapp.generated.resources.CinzelDecorative_Black
import wonderouscompose.composeapp.generated.resources.CinzelDecorative_Bold
import wonderouscompose.composeapp.generated.resources.CinzelDecorative_Regular
import wonderouscompose.composeapp.generated.resources.Gotu_Regular
import wonderouscompose.composeapp.generated.resources.MaShanZheng_Regular
import wonderouscompose.composeapp.generated.resources.NotoSansSC_Bold
import wonderouscompose.composeapp.generated.resources.NotoSansSC_Regular
import wonderouscompose.composeapp.generated.resources.NotoSans_Bold
import wonderouscompose.composeapp.generated.resources.NotoSans_Regular
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

internal val Cinzel
    @Composable get() = FontFamily(
        Font(Res.font.CinzelDecorative_Regular, FontWeight.Normal, FontStyle.Normal),
        Font(Res.font.CinzelDecorative_Bold, FontWeight.Bold, FontStyle.Normal),
        Font(Res.font.CinzelDecorative_Black, FontWeight.Black, FontStyle.Normal),
    )


internal val Raleway
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


internal val MaShanZheng
    @Composable get() = FontFamily(
        Font(Res.font.MaShanZheng_Regular, FontWeight.Normal, FontStyle.Normal),
    )


internal val B612Mono
    @Composable get() = FontFamily(
        Font(Res.font.B612Mono_Regular, FontWeight.Normal, FontStyle.Normal),
    )


internal val TenorSans
    @Composable get() = FontFamily(
        Font(Res.font.TenorSans_Regular, FontWeight.Normal, FontStyle.Normal),
    )


internal val YesevaOne
    @Composable get() = FontFamily(
        Font(Res.font.YesevaOne_Regular, FontWeight.Normal, FontStyle.Normal),
    )

internal val Gotu
    @Composable get() = FontFamily(
        Font(Res.font.Gotu_Regular, FontWeight.Normal, FontStyle.Normal),
    )


internal val NotoSans
    @Composable get() = FontFamily(
        Font(Res.font.NotoSans_Regular, FontWeight.Normal, FontStyle.Normal),
        Font(Res.font.NotoSans_Bold, FontWeight.Bold, FontStyle.Normal),
    )

internal val NotoSansSc
    @Composable get() = FontFamily(
        Font(Res.font.NotoSansSC_Regular, FontWeight.Normal, FontStyle.Normal),
        Font(Res.font.NotoSansSC_Bold, FontWeight.Bold, FontStyle.Normal),
    )
