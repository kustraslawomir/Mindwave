package skustra.focusflow.ui.composables.session

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import skustra.focusflow.data.alias.Minute
import skustra.focusflow.data.exceptions.SessionAlreadyCompletedException
import skustra.focusflow.data.session.Session
import skustra.focusflow.data.timer.TimerState
import skustra.focusflow.domain.logs.AppLog
import skustra.focusflow.domain.usecase.resources.DrawableProvider
import skustra.focusflow.domain.usecase.session.SessionConfig
import skustra.focusflow.domain.usecase.session.Timer
import javax.inject.Inject

@HiltViewModel
class SessionViewModel @Inject constructor(
    private val timer: Timer,
    val resourceManager: DrawableProvider
) : ViewModel() {

    private val _sessionMutableStateFlow = MutableSharedFlow<Session>()
    val sessionStateFlow: SharedFlow<Session> = _sessionMutableStateFlow

    var currentSessionState = SessionConfig.generate()

    private var durationChosenByUser = SessionConfig.DEFAULT_DURATION
    private var skipBreaks = false

    init {
        viewModelScope.launch {
            timer.getCurrentTimerState().collect { timerState ->
                if (timerState == TimerState.Completed) {
                    try {
                        currentSessionState.activateTheNextPartOfTheSession()
                        timer.start(
                            sessionDuration = currentSessionState
                                .currentSessionPart()
                                .sessionPartDuration,
                            scope = viewModelScope
                        )
                    } catch (exception: SessionAlreadyCompletedException) {
                        exception.printStackTrace()
                        timer.stop()
                        return@collect
                    }
                }

                emiTimerState(timerState)
                AppLog.debugSession(currentSessionState.deepCopy())
            }
        }
    }

    fun updateDuration(durationChosenByUser: Minute) {
        this.durationChosenByUser = durationChosenByUser
        viewModelScope.launch {
            emitSession(SessionConfig.generate(durationChosenByUser, skipBreaks))
        }
    }

    fun skipBreaks(skipBreaks: Boolean) {
        this.skipBreaks = skipBreaks
        viewModelScope.launch {
            emitSession(SessionConfig.generate(durationChosenByUser, skipBreaks))
        }
    }

    fun createSession() {
        viewModelScope.launch {
            currentSessionState = SessionConfig.generate(durationChosenByUser, skipBreaks)
            timer.start(currentSessionState.currentSessionPart().sessionPartDuration, this)
        }
    }

    fun pauseSession() {
        viewModelScope.launch {
            timer.pause()
        }
    }

    fun resumeSession() {
        viewModelScope.launch {
            timer.resume()
        }
    }

    fun stopSession() {
        viewModelScope.launch {
            timer.stop()
        }
    }

    private suspend fun emiTimerState(state: TimerState) {
        _sessionMutableStateFlow.emit(currentSessionState.apply {
            currentTimerState = state
        }.deepCopy())
    }

    private suspend fun emitSession(session: Session) {
        _sessionMutableStateFlow.emit(session.deepCopy())
    }
}