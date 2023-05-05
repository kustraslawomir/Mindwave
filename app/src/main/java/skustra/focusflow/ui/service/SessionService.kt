package skustra.focusflow.ui.service

import android.app.Service
import android.content.Intent
import android.util.Log
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import skustra.focusflow.data.model.timer.TimerState
import skustra.focusflow.domain.usecase.timer.Timer
import skustra.focusflow.domain.utilities.logs.AppLog
import skustra.focusflow.ui.notification.ServiceNotification
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class SessionService : Service() {

    @Inject
    lateinit var timer: Timer

    private val serviceScope = MainScope()

    private val serviceNotification by lazy {
        ServiceNotification(this)
    }

    override fun onCreate() {


        serviceScope.launch {
            timer.getCurrentTimerState().collect { timerState ->
                AppLog.notificationState(timerState)
                when (timerState) {
                    is TimerState.InProgress -> serviceNotification.updateInProgressState(
                        timerState
                    )
                    is TimerState.Paused -> serviceNotification.updateInPausedState()
                    else -> stopForeground(STOP_FOREGROUND_REMOVE)
                }
            }
        }

        serviceScope.launch {
            Timber.d("NOTIFICATION $timer")
        }

        super.onCreate()
    }

    override fun onDestroy() {
        super.onDestroy()
        stopForeground(STOP_FOREGROUND_REMOVE)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        startForeground(
            serviceNotification.notificationId,
            serviceNotification.createNotification()
        )
        return START_STICKY
    }

    override fun onBind(intent: Intent?) = null
}