package skustra.focusflow.ui.notification

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import androidx.core.app.NotificationCompat
import skustra.focusflow.R
import skustra.focusflow.data.model.timer.TimerState
import skustra.focusflow.ui.localization.LocalizationKey
import skustra.focusflow.ui.localization.LocalizationManager
import java.util.*

class ServiceNotification(private val context: Context) {

    private val notificationManager = context
        .getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    private val channelId = UUID.randomUUID().toString()

    val notificationId = UUID.randomUUID().hashCode()

    fun createNotification(): Notification {
        createNotificationChannel()
        return getNotification(LocalizationManager.getText(LocalizationKey.SessionInProgressDescription))
    }

    private fun getNotification(contentText: String): Notification {
        return NotificationCompat.Builder(context, channelId)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle(LocalizationManager.getText(LocalizationKey.SessionInProgress))
            .setContentText(contentText)
            .setOngoing(true)
            .setOnlyAlertOnce(true)
            .build()
    }

    private fun createNotificationChannel() {
        return notificationManager.createNotificationChannel(
            NotificationChannel(
                channelId,
                context.getString(R.string.app_name),
                NotificationManager.IMPORTANCE_HIGH
            )
        )
    }

    fun updateInProgressState(timerState: TimerState.InProgress) {
        val contentText = "${timerState.progress.minutesLeft} ${LocalizationManager.getText(LocalizationKey.MinutesShort)}"
        notificationManager.notify(notificationId, getNotification(contentText))
    }

    fun updateInPausedState() {
        notificationManager.notify(
            notificationId, getNotification(
                LocalizationManager.getText(LocalizationKey.Break)
            )
        )
    }
}