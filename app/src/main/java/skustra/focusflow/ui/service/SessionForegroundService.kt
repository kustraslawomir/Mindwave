package skustra.focusflow.ui.service

import android.app.Service
import android.content.Intent
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import skustra.focusflow.data.model.timer.TimerState
import skustra.focusflow.ui.composables.session.TimerStateHandler
import skustra.focusflow.ui.notification.SessionServiceNotificationManager
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class SessionForegroundService @Inject constructor() : Service() {

    private val serviceScope = MainScope()

    @Inject
    lateinit var timerStateHandler: TimerStateHandler

    @Inject
    lateinit var sessionServiceNotificationManager: SessionServiceNotificationManager

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        timerStateHandler.apply {
            init(serviceScope)
            startSession(
                getDuration(intent), shouldSkipBreaks(intent)
            )
        }
        return START_STICKY
    }

    override fun onCreate() {
        super.onCreate()

        startForeground(
            sessionServiceNotificationManager.getNotificationId(),
            sessionServiceNotificationManager.createNotification()
        )

        serviceScope.launch {
            timerStateHandler.sessionStateFlow.collect { session ->
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
        return intent?.extras?.getBoolean(SKIP_BREAKS) ?: throw IllegalArgumentException()
    }

    private fun getDuration(intent: Intent?) =
        intent?.extras?.getInt(DURATION_CHOSEN_BY_USER) ?: throw IllegalArgumentException()

    override fun onDestroy() {
        Timber.e("Destroyed session service")
        super.onDestroy()
    }

    override fun onBind(intent: Intent?) = null

    companion object {
        const val DURATION_CHOSEN_BY_USER = "durationChosenByUser"
        const val SKIP_BREAKS = "skipBreaks"
    }
}