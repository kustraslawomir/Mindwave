package skustra.focusflow.ui.localization


object LocalizationManager {
    private val localize = Localize()

    private val localizedTexts: Map<SupportedLanguage, Map<LocalizationKey, String>> = mapOf(
        SupportedLanguage.Polish to mapOf(
            LocalizationKey.Remaining to "Pozostało",
            LocalizationKey.Start to "Start",
            LocalizationKey.Pause to "Wstrzymaj",
            LocalizationKey.Resume to "Wznów",
            LocalizationKey.Stop to "Zatrzymaj",
            LocalizationKey.MinutesShort to "min.",
            LocalizationKey.Break to "Przerwa",
        ),
        SupportedLanguage.English to mapOf(
            LocalizationKey.Remaining to "Remaining",
            LocalizationKey.Start to "Start",
            LocalizationKey.Pause to "Pause",
            LocalizationKey.Resume to "Resume",
            LocalizationKey.Stop to "Stop",
            LocalizationKey.MinutesShort to "min.",
            LocalizationKey.Break to "Break",
        )
    )

    fun getText(key: LocalizationKey): String {
        val currentLocaleCountryCode = localize.currentLanguage()
        return localizedTexts[currentLocaleCountryCode]?.get(key)
            ?: "Missing: $key for ${localize.currentLanguage()}"
    }
}