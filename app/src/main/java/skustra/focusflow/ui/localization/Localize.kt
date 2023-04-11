package skustra.focusflow.ui.localization

import androidx.compose.runtime.mutableStateOf
import java.util.*

class Localize {
    private val currentLocale = mutableStateOf(Locale.getDefault())

    fun setCurrentLocale(locale: Locale) {
        currentLocale.value = locale
    }

    fun currentLanguage(): SupportedLanguage {
        return when (currentLocale.value.country) {
            SupportedLanguage.English.countryCode -> SupportedLanguage.English
            SupportedLanguage.Polish.countryCode -> SupportedLanguage.Polish
            else -> SupportedLanguage.English
        }
    }
}