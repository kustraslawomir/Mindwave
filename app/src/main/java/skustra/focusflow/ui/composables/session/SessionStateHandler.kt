package skustra.focusflow.ui.composables.session

import android.content.Context
import android.content.Intent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import skustra.focusflow.data.model.alias.Minute
import skustra.focusflow.data.model.exceptions.SessionAlreadyCompletedException
import skustra.focusflow.data.model.session.Session
import skustra.focusflow.data.model.timer.TimerState
import skustra.focusflow.domain.usecase.session.SessionCreator
import skustra.focusflow.domain.usecase.timer.SessionStateEmitter
import skustra.focusflow.domain.usecase.timer.SessionStateEmitterImpl
import skustra.focusflow.domain.utilities.logs.AppLog
import skustra.focusflow.ui.extensions.vibratePhone
import skustra.focusflow.ui.service.SessionForegroundService
import timber.log.Timber

class SessionStateHandler(private val applicationContext: Context) {

    private val stateEmitter: SessionStateEmitter = SessionStateEmitterImpl()

    var currentSessionState = SessionCreator.generate()
    private val _sessionMutableStateFlow = MutableStateFlow(currentSessionState)
    val sessionStateFlow: StateFlow<Session> = _sessionMutableStateFlow

    private lateinit var sessionScope: CoroutineScope

    fun init(scope: CoroutineScope) {
        this.sessionScope = scope
        sessionScope.launch {
            stateEmitter.getCurrentState().stateIn(
                scope = sessionScope,
                initialValue = currentSessionState,
                started = SharingStarted.WhileSubscribed(5000)
            ).collect { timerState ->
                if (timerState is TimerState) {
                    handleNewTimerState(timerState)
                }
            }
        }
    }

    fun startSession(durationChosenByUser: Minute, skipBreaks: Boolean) {
        sessionScope.launch {
            currentSessionState = SessionCreator.generate(durationChosenByUser, skipBreaks)
            Timber.d("NOTIFICATION $stateEmitter")
            stateEmitter.start(
                currentSessionState.currentSessionPart().sessionPartDuration,
                sessionScope
            )
        }
    }

    suspend fun emitSession(session: Session) {
        _sessionMutableStateFlow.emit(session.deepCopy())
    }

    fun pauseSession() {
        stateEmitter.pause()
    }

    fun resumeSession() {
        stateEmitter.resume()
    }

    fun stopSession() {
        stateEmitter.stop()
        applicationContext.stopService(
            Intent(
                applicationContext,
                SessionForegroundService::class.java
            )
        )
    }

    private suspend fun handleNewTimerState(
        timerState: TimerState
    ) {
        if (timerState == TimerState.Completed) {
            try {
                applicationContext.vibratePhone()
                currentSessionState.activateTheNextPartOfTheSession()
                stateEmitter.start(
                    sessionDuration = currentSessionState
                        .currentSessionPart()
                        .sessionPartDuration,
                    scope = sessionScope
                )
            } catch (exception: SessionAlreadyCompletedException) {
                exception.printStackTrace()
                stopSession()
                return
            }
        }

        emiTimerState(timerState)
        AppLog.debugSession(currentSessionState.deepCopy())
    }

    private suspend fun emiTimerState(state: TimerState) {
        Timber.d("Emit: $state ${state is TimerState.Completed}")
        _sessionMutableStateFlow.emit(currentSessionState.apply {
            currentTimerState = state
        }.copy())
    }

}