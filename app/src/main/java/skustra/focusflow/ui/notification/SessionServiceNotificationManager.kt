package skustra.focusflow.ui.notification

import android.app.Notification
import skustra.focusflow.data.model.timer.TimerState

interface SessionServiceNotificationManager {

    fun createNotification(): Notification

    fun updateInProgressState(timerState: TimerState.InProgress)

    fun updateInPausedState()

    fun getNotificationId(): Int
}