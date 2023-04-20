package skustra.focusflow.ui.composables.session

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import skustra.focusflow.data.timer.TimerState
import skustra.focusflow.data.alias.Minute
import skustra.focusflow.data.session.SessionState
import skustra.focusflow.domain.logs.AppLog
import skustra.focusflow.domain.usecase.resources.DrawableProvider
import skustra.focusflow.domain.usecase.session.Timer
import javax.inject.Inject

@HiltViewModel
class SessionViewModel @Inject constructor(
    private val timer: Timer,
    val resourceManager: DrawableProvider
) : ViewModel() {

    private val _sessionMutableStateFlow: MutableStateFlow<SessionState> =
        MutableStateFlow(SessionState.draft())

    val sessionStateFlow: StateFlow<SessionState> = _sessionMutableStateFlow

    init {
        viewModelScope.launch {
            timer.getCurrentTimerState().collect { state ->
                AppLog.debugSession(state)
                _sessionMutableStateFlow.emit(sessionStateFlow.value.apply {
                    currentTimerState = state
                })
            }
        }
    }

    fun createSession(minute: Minute) {
        viewModelScope.launch {
            timer.run(minute, this)
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
}