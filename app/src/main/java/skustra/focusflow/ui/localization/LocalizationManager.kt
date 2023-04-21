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
            LocalizationKey.CreateSession to "Rozpocznij sesję koncentracji",
            LocalizationKey.NoBreakIncluded to "Nie będziesz mieć już przerw",
            LocalizationKey.SingleBreak to "Będziesz mieć 1 przerwę",
            LocalizationKey.BreakCount to "Liczba przerw:",
        ),
        SupportedLanguage.English to mapOf(
            LocalizationKey.Remaining to "Remaining",
            LocalizationKey.Start to "Start",
            LocalizationKey.Pause to "Pause",
            LocalizationKey.Resume to "Resume",
            LocalizationKey.Stop to "Stop",
            LocalizationKey.MinutesShort to "min.",
            LocalizationKey.Break to "Break",
            LocalizationKey.CreateSession to "Start a concentration session",
            LocalizationKey.NoBreakIncluded to "No more breaks",
            LocalizationKey.SingleBreak to "You will have 1 break",
            LocalizationKey.BreakCount to "Number of breaks:",
        )
    )

    fun getText(key: LocalizationKey): String {
        val currentLocaleCountryCode = localize.currentLanguage()
        return localizedTexts[currentLocaleCountryCode]?.get(key)
            ?: "Missing: $key for ${localize.currentLanguage()}"
    }
}