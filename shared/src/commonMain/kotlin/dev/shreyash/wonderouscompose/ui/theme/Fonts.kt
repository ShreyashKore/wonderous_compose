package dev.shreyash.wonderouscompose.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.intl.Locale
import org.jetbrains.compose.resources.Font
import wonderous_compose.shared.generated.resources.B612Mono_Regular
import wonderous_compose.shared.generated.resources.CinzelDecorative_Black
import wonderous_compose.shared.generated.resources.CinzelDecorative_Bold
import wonderous_compose.shared.generated.resources.CinzelDecorative_Regular
import wonderous_compose.shared.generated.resources.Gotu_Regular
import wonderous_compose.shared.generated.resources.MaShanZheng_Regular
import wonderous_compose.shared.generated.resources.NotoSansSC_Bold
import wonderous_compose.shared.generated.resources.NotoSansSC_Regular
import wonderous_compose.shared.generated.resources.NotoSans_Bold
import wonderous_compose.shared.generated.resources.NotoSans_Regular
import wonderous_compose.shared.generated.resources.Raleway_Bold
import wonderous_compose.shared.generated.resources.Raleway_BoldItalic
import wonderous_compose.shared.generated.resources.Raleway_ExtraBold
import wonderous_compose.shared.generated.resources.Raleway_ExtraBoldItalic
import wonderous_compose.shared.generated.resources.Raleway_Italic
import wonderous_compose.shared.generated.resources.Raleway_Medium
import wonderous_compose.shared.generated.resources.Raleway_MediumItalic
import wonderous_compose.shared.generated.resources.Raleway_Regular
import wonderous_compose.shared.generated.resources.Res
import wonderous_compose.shared.generated.resources.TenorSans_Regular
import wonderous_compose.shared.generated.resources.YesevaOne_Regular

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

val defaultFontFamily: FontFamily
    @Composable get() = when (Locale.current.language) {
        "zh" -> NotoSansSc
        else -> NotoSans
    }