package skustra.focusflow.ui.service

import android.app.Service
import android.content.Intent
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import skustra.focusflow.data.model.timer.TimerState
import skustra.focusflow.domain.usecase.sessionstate.SessionStateHandler
import skustra.focusflow.ui.notification.SessionServiceNotificationManager
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class SessionForegroundService @Inject constructor() : Service() {

    private val serviceScope = CoroutineScope(Job() + Dispatchers.Main)

    @Inject
    lateinit var sessionStateHandler: SessionStateHandler

    @Inject
    lateinit var sessionServiceNotificationManager: SessionServiceNotificationManager

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        sessionStateHandler.apply {
            init(serviceScope)
            startSession(getDuration(intent), shouldSkipBreaks(intent))
        }
        return START_STICKY
    }

    override fun onCreate() {
        super.onCreate()
        Timber.w("On create service")
        startForeground(
            sessionServiceNotificationManager.getNotificationId(),
            sessionServiceNotificationManager.createNotification()
        )

        serviceScope.launch {
            sessionStateHandler.sessionStateFlow.collect { session ->
                Timber.d("instance session composable at service: ${sessionStateHandler.sessionStateFlow}")
                when (val state = session.currentTimerState) {
                    is TimerState.InProgress -> {
                        sessionServiceNotificationManager.updateInProgressState(state)
                    }
                    is TimerState.Paused -> {
                        sessionServiceNotificationManager.updateInPausedState()
                    }
                    else -> {
                        //ignore
                    }
                }
            }
        }
    }

    private fun shouldSkipBreaks(intent: Intent?): Boolean {
        return intent?.extras?.getBoolean(SKIP_BREAKS) ?: throw NullPointerException()
    }

    private fun getDuration(intent: Intent?) =
        intent?.extras?.getInt(DURATION_CHOSEN_BY_USER) ?: throw NullPointerException()

    override fun onDestroy() {
        Timber.e("Destroyed session service")
        stopForeground(STOP_FOREGROUND_REMOVE)
        serviceScope.cancel()
        super.onDestroy()
    }

    override fun onBind(intent: Intent?) = null

    companion object {
        const val DURATION_CHOSEN_BY_USER = "durationChosenByUser"
        const val SKIP_BREAKS = "skipBreaks"
    }
}