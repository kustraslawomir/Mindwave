package skustra.focusflow.ui.utilities.logs

import skustra.focusflow.data.model.session.Session
import skustra.focusflow.data.model.timer.TimerState
import skustra.focusflow.ui.features.session.SessionUiEvent
import timber.log.Timber

object AppLog {

    fun debugSession(message: Session) {
        Timber.d("[SESSION] current part: ${message.currentPartCounter} of ${message.parts.size} | ${message.currentTimerState}")
    }

    fun notificationState(timerState: TimerState) {
        Timber.d("[NOTIFICATION] $timerState")
    }

    fun log(value: String) {
        Timber.d(value)
    }

    fun navigate(route: String) {
        Timber.w("[NAVIGATE] navigate to: $route")
    }

    fun navigateAndPopBackStack(route: String, popUpTo: String? = "") {
        Timber.w("[NAVIGATE] navigate and pop back stack to: $route | pop up to: $popUpTo")
    }

    fun navigateSaved(route: String, popUpTo: String? = "") {
        Timber.w("[NAVIGATE] navigate saved to: $route | pop up to: $popUpTo")
    }

    fun clearAndNavigate(route: String) {
        Timber.w("[NAVIGATE] clear and navigate to: $route")
    }

    fun logNavigationPop() {
        Timber.w("[NAVIGATE] pop up")
    }

    fun onSessionUiEvent(event: SessionUiEvent) {
        Timber.w("on session ui event change: $event")
    }

    fun logTimerStateCollection(timerState: Any) {
        Timber.d("collect timer state: $timerState")
    }
}