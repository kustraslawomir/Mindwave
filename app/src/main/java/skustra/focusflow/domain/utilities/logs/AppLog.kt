package skustra.focusflow.domain.utilities.logs

import skustra.focusflow.data.model.session.Session
import skustra.focusflow.data.model.timer.TimerState
import timber.log.Timber

object AppLog {

    fun debugSession(message: Session) {
        Timber.d("[SESSION] current part: ${message.currentPartCounter} of ${message.parts.size} | ${message.currentTimerState}")
    }

    fun notificationState(timerState: TimerState) {
        Timber.d("[NOTIFICATION] $timerState")
    }

    fun log(value: String){
        Timber.d(value)
    }
}