package skustra.focusflow.ui.composables.session

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import skustra.focusflow.data.exceptions.SessionAlreadyCompletedException
import skustra.focusflow.data.session.SessionState
import skustra.focusflow.data.timer.TimerState
import skustra.focusflow.domain.logs.AppLog
import skustra.focusflow.domain.usecase.resources.DrawableProvider
import skustra.focusflow.domain.usecase.session.Timer
import javax.inject.Inject

@HiltViewModel
class SessionViewModel @Inject constructor(
    private val timer: Timer,
    val resourceManager: DrawableProvider
) : ViewModel() {

    private val _sessionMutableStateFlow: MutableSharedFlow<SessionState> =
        MutableSharedFlow()
    val sessionStateFlow: SharedFlow<SessionState> = _sessionMutableStateFlow
    private var currentSessionState = SessionState.draft()

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
                AppLog.debugSession(currentSessionState)
            }
        }
    }

    fun createSession(sessionState: SessionState) {
        viewModelScope.launch {
            currentSessionState = sessionState
            timer.start(sessionState.currentSessionPart().sessionPartDuration, this)
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
}