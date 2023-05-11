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
            LocalizationKey.NoBreakIncluded to "Nie będziesz mieć żadnych przerw",
            LocalizationKey.SingleBreak to "Będziesz mieć 1 przerwę",
            LocalizationKey.BreakCount to "Liczba przerw:",
            LocalizationKey.SkipBreaks to "Pomiń przerwy",
            LocalizationKey.SessionInProgress to "Twoja sesja jest w toku",
            LocalizationKey.SessionInProgressDescription to "Utrzymaj skupienie podczas pracy",
            LocalizationKey.SessionPaused to "Sesja wstrzymana",
            LocalizationKey.Mon to "Pon",
            LocalizationKey.Tue to "Wt",
            LocalizationKey.Wed to "Śr",
            LocalizationKey.Thu to "Czw",
            LocalizationKey.Fri to "Pt",
            LocalizationKey.Sat to "Sob",
            LocalizationKey.Sun to "Ndz"

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
            LocalizationKey.NoBreakIncluded to "You won't have any breaks",
            LocalizationKey.SingleBreak to "You will have 1 break",
            LocalizationKey.BreakCount to "Number of breaks:",
            LocalizationKey.SkipBreaks to "Skip breaks",
            LocalizationKey.SessionInProgress to "Your session is in progress",
            LocalizationKey.SessionInProgressDescription to "Stay focused while you work",
            LocalizationKey.SessionPaused to "Session paused",
            LocalizationKey.Mon to "Mon",
            LocalizationKey.Tue to "Tue",
            LocalizationKey.Wed to "Wed",
            LocalizationKey.Thu to "Thu",
            LocalizationKey.Fri to "Fri",
            LocalizationKey.Sat to "Sat",
            LocalizationKey.Sun to "Sun"
        )
    )

    fun getText(key: LocalizationKey): String {
        val currentLocaleCountryCode = localize.currentLanguage()
        return localizedTexts[currentLocaleCountryCode]?.get(key)
            ?: "Missing: $key for ${localize.currentLanguage()}"
    }
}