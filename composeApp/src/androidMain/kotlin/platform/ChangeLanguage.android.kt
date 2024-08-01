package platform

import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat

actual fun changeLanguage(language: String) {
    val localListCompat = LocaleListCompat.forLanguageTags(language)
    AppCompatDelegate
        .setApplicationLocales(localListCompat)
}