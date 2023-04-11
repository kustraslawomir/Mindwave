package skustra.focusflow.ui.localization

import androidx.compose.runtime.mutableStateOf
import java.util.*

class Localize {
    private val currentLocale = mutableStateOf(Locale.getDefault())

    fun setCurrentLocale(locale: Locale) {
        currentLocale.value = locale
    }

    fun currentLocale(): String {
        return currentLocale.value.country
    }
}