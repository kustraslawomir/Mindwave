package skustra.focusflow.ui.localization

sealed class SupportedLanguage(val countryCode : String) {
    object English : SupportedLanguage("EN")
    object Polish : SupportedLanguage("PL")
}