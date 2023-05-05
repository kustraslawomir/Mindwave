package skustra.focusflow.ui.service

import android.app.Service
import android.content.Intent
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import skustra.focusflow.data.model.timer.TimerState
import skustra.focusflow.domain.usecase.timer.Timer
import skustra.focusflow.ui.notification.SessionServiceNotificationManager
import skustra.focusflow.ui.notification.SessionServiceNotificationManagerImpl
import javax.inject.Inject

@AndroidEntryPoint
class SessionForegroundService : Service() {

    @Inject
    lateinit var timer: Timer

    @Inject
    lateinit var sessionServiceNotificationManager: SessionServiceNotificationManager

    private val serviceScope = MainScope()

    override fun onBind(intent: Intent?) = null

    override fun onCreate() {
        serviceScope.launch {
            timer.getCurrentTimerState().collect { timerState ->
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
            sessionServiceNotificationManager.getNotificationId(),
            sessionServiceNotificationManager.createNotification()
        )
        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        stopForeground(STOP_FOREGROUND_REMOVE)
    }
}