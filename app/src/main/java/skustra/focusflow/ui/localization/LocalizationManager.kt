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
            LocalizationKey.Sun to "Ndz",
            LocalizationKey.DailyGoal to "Cel",
            LocalizationKey.YourProgress to "Postęp",
            LocalizationKey.ChartDescription to "Wykres przedstawia Twój rozwój w czasie, pomagając świadomie podejmować decyzje dotyczące zarządzania czasem. Możesz również porównywać swój czas pracy w różnych okresach, co pomoże Ci w identyfikacji trendów i wykrywaniu ewentualnych nieefektywności.\n\nPamiętaj, że Twoje zaangażowanie i systematyczność mają bezpośredni wpływ na osiąganie sukcesów, a aplikacja jest tu, aby Ci w tym pomóc.",
            LocalizationKey.SessionIdleTitle to "Przygotuj się do sesji koncentracji",
            LocalizationKey.SessionIdleDescription to "Daj sobie czas i przestrzeń do skupienia, a z pewnością osiągniesz swoje cele. Powodzenia!",
            LocalizationKey.SessionPauseTitle to "Sesja wstrzymana",
            LocalizationKey.SessionPauseDescription to "Gdy tylko poczujesz się gotowy, wróć do sesji",
            LocalizationKey.SessionInProgressTitle to "Skup się",
            LocalizationKey.SessionBreakTitle to "Odpocznij",
            LocalizationKey.SessionBreakDescription to "Skorzystaj z przerwy na relaks, aby zregenerować umysł i ciało, angażując się w medytację, krótką przechadzkę lub głębokie oddychanie",
            LocalizationKey.PostPermissionNeedMessage to "Pozwolenie na powiadomienia pomoże Ci śledzić postęp zadań za pośrednictwem paska powiadomień.",
            LocalizationKey.PostPermissionNeedMessageRationale to "Pozwolenie na powiadomienia pomoże Ci śledzić postęp zadań za pośrednictwem paska powiadomień. Jeśli chcesz udzielić pozwolenia na powiadomienia, prosimy o kliknięcie w odpowiednią opcję w ustawieniach aplikacji.",
            LocalizationKey.Allow to "Zezwól",


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
            LocalizationKey.Sun to "Sun",
            LocalizationKey.DailyGoal to "Daily goal",
            LocalizationKey.YourProgress to "Your progress",
            LocalizationKey.ChartDescription to "The chart represents your growth over time, helping you make conscious decisions regarding time management. You can also compare your work time across different periods, which will aid in identifying trends and detecting potential inefficiencies.\n\nRemember that your commitment and consistency directly impact your success, and the application is here to assist you in achieving that.",
            LocalizationKey.SessionIdleTitle to "Get ready for a concentration session",
            LocalizationKey.SessionIdleDescription to "Give yourself time and space to focus, and you will undoubtedly achieve your goals. Best of luck!",
            LocalizationKey.SessionPauseTitle to "Session paused",
            LocalizationKey.SessionPauseDescription to "Return to the session as soon as you are ready",
            LocalizationKey.SessionInProgressTitle to "Focus",
            LocalizationKey.SessionBreakTitle to "Rest",
            LocalizationKey.SessionBreakDescription to "Take this time to relax your mind and body, engage in activities such as meditation, a short walk, or deep breathing",
            LocalizationKey.PostPermissionNeedMessage to "Granting permission for notifications will help you track the progress of your tasks through the notification bar.",
            LocalizationKey.PostPermissionNeedMessageRationale to "Granting permission for notifications will help you track the progress of your tasks through the notification bar. Thank you in advance for your consent. If you would like to grant permission for notifications, please click on the appropriate option in the application settings.",
            LocalizationKey.Allow to "Allow",


            )
    )

    fun getText(key: LocalizationKey): String {
        val currentLocaleCountryCode = localize.currentLanguage()
        return localizedTexts[currentLocaleCountryCode]?.get(key)
            ?: "Missing: $key for ${localize.currentLanguage()}"
    }
}