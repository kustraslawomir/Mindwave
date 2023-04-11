package skustra.focusflow.ui.localization

import java.util.*

object LocalizationManager {

    private const val en = "EN"
    private const val pl = "PL"

    private val localize = Localize()

    fun getText(key: LocalizationKey): String {
        var currentLocale = localize.currentLocale()
        if (currentLocale != en && currentLocale != pl) {
            currentLocale = en
        }
        return localizedTexts[currentLocale]?.get(key)
            ?: "Missing: $key for ${localize.currentLocale()}"
    }

    private val localizedTexts = mapOf(
        pl to mapOf(
            LocalizationKey.Remaining to "Pozostało",
            LocalizationKey.Start to "Start",
            LocalizationKey.Pause to "Wstrzymaj",
            LocalizationKey.Resume to "Wznów",
            LocalizationKey.Stop to "Zatrzymaj",
            LocalizationKey.MinutesShort to "min.",
        ),
        en to mapOf(
            LocalizationKey.Remaining to "Remaining",
            LocalizationKey.Start to "Start",
            LocalizationKey.Pause to "Pause",
            LocalizationKey.Resume to "Resume",
            LocalizationKey.Stop to "Stop",
            LocalizationKey.MinutesShort to "min.",
        )
    )
}