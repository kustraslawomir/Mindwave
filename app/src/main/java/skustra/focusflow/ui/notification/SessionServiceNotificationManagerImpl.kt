package skustra.focusflow.ui.notification

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import skustra.focusflow.BuildConfig
import skustra.focusflow.R
import skustra.focusflow.data.model.timer.TimerState
import skustra.focusflow.ui.activity.MainActivity
import skustra.focusflow.ui.localization.LocalizationKey
import skustra.focusflow.ui.localization.LocalizationManager
import java.util.*

class SessionServiceNotificationManagerImpl(
    private val applicationContext: Context
) : SessionServiceNotificationManager {

    private val notificationId = UUID.randomUUID().hashCode()

    private val channelId = UUID.randomUUID().toString()
    private val notificationManager =
        applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    override fun createNotification(): Notification {
        createNotificationChannel()
        return getNotification(LocalizationManager.getText(LocalizationKey.SessionInProgressDescription))
    }

    override fun updateInProgressState(timerState: TimerState.InProgress) {
        val contentText =
            "${timerState.progress.minutesLeft} ${LocalizationManager.getText(LocalizationKey.MinutesShort)}"
        notificationManager.notify(notificationId, getNotification(contentText))
    }

    override fun updateInPausedState() {
        notificationManager.notify(
            notificationId, getNotification(
                LocalizationManager.getText(LocalizationKey.SessionPaused)
            )
        )
    }

    override fun getNotificationId(): Int {
        return notificationId
    }

    private fun getNotification(contentText: String): Notification {
        return NotificationCompat.Builder(applicationContext, channelId)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle(LocalizationManager.getText(LocalizationKey.SessionInProgress))
            .setContentIntent(buildIntent())
            .setContentText(contentText)
            .setOngoing(true)
            .setOnlyAlertOnce(true)
            .build()
    }

    private fun createNotificationChannel() {
        return notificationManager.createNotificationChannel(
            NotificationChannel(
                channelId,
                BuildConfig.APPLICATION_ID,
                NotificationManager.IMPORTANCE_LOW
            ).apply {
                setShowBadge(false)
            }
        )
    }

    private fun buildIntent(): PendingIntent? {
        return PendingIntent.getActivity(
            applicationContext,
            0,
            Intent(applicationContext, MainActivity::class.java),
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
    }
}