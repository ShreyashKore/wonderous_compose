package localization

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.staticCompositionLocalOf

val LocalLocaleState = staticCompositionLocalOf {
    LocaleState()
}

val LocalStrings @Composable get() = LocalLocaleState.current.current


@Composable
fun rememberLocaleState(): LocaleState {
    return remember { LocaleState() }
}

class LocaleState {
    var languageTag: String by mutableStateOf("en")
        private set

    fun setLanguage(languageTag: String) {
        this.languageTag = languageTag
    }

    val current: Strings
        get() = when (languageTag) {
            "en" -> EnStrings
            "hi" -> HiStrings
            "zh" -> ZhStrings
            else -> EnStrings
        }
}

