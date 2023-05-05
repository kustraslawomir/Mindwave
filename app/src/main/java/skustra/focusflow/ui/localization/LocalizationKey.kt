package skustra.focusflow.ui.localization

sealed class LocalizationKey {
   object Remaining : LocalizationKey()
   object Start : LocalizationKey()
   object Pause : LocalizationKey()
   object Resume : LocalizationKey()
   object Stop : LocalizationKey()
   object MinutesShort : LocalizationKey()
   object Break : LocalizationKey()
   object CreateSession : LocalizationKey()
   object NoBreakIncluded : LocalizationKey()
   object SingleBreak : LocalizationKey()
   object BreakCount : LocalizationKey()
   object SkipBreaks : LocalizationKey()
   object SessionInProgress : LocalizationKey()
   object SessionInProgressDescription : LocalizationKey()

}