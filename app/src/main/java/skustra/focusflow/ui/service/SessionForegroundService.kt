package skustra.focusflow.ui.service

import android.app.Service
import android.content.Intent
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import skustra.focusflow.data.model.timer.TimerState
import skustra.focusflow.domain.usecase.timer.Timer
import skustra.focusflow.domain.utilities.logs.AppLog
import skustra.focusflow.ui.notification.SessionServiceNotificationManager
import javax.inject.Inject

@AndroidEntryPoint
class SessionForegroundService : Service() {

    @Inject
    lateinit var timer: Timer

    private val serviceScope = MainScope()

    private val sessionServiceNotificationManager by lazy {
        SessionServiceNotificationManager(this)
    }

    override fun onBind(intent: Intent?) = null

    override fun onCreate() {
        serviceScope.launch {
            timer.getCurrentTimerState().collect { timerState ->
                AppLog.notificationState(timerState)
                when (timerState) {
                    is TimerState.InProgress -> sessionServiceNotificationManager.updateInProgressState(
                        timerState
                    )
                    is TimerState.Paused -> sessionServiceNotificationManager.updateInPausedState()
                    else -> stopForeground(STOP_FOREGROUND_REMOVE)
                }
            }
        }

        super.onCreate()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        startForeground(
            sessionServiceNotificationManager.notificationId,
            sessionServiceNotificationManager.createNotification()
        )
        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        stopForeground(STOP_FOREGROUND_REMOVE)
    }
}