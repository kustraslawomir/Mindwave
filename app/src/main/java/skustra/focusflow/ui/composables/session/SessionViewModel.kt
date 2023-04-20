package skustra.focusflow.ui.composables.session

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import skustra.focusflow.data.TimerState
import skustra.focusflow.data.alias.Minute
import skustra.focusflow.domain.logs.AppLog
import skustra.focusflow.domain.usecase.resources.DrawableProvider
import skustra.focusflow.domain.usecase.session.Timer
import javax.inject.Inject

@HiltViewModel
class SessionViewModel @Inject constructor(
    private val sessionManager: Timer,
    val resourceManager: DrawableProvider
) : ViewModel() {

    private val _sessionMutableStateFlow: MutableStateFlow<TimerState> =
        MutableStateFlow(TimerState.Idle)

    val sessionStateFlow: StateFlow<TimerState> = _sessionMutableStateFlow

    init {
        viewModelScope.launch {
            sessionManager.getCurrentTimerState().collect { state ->
                AppLog.debugSession(state)
                _sessionMutableStateFlow.emit(state)
            }
        }
    }

    fun createSession(minute: Minute) {
        viewModelScope.launch {
            sessionManager.run(minute, this)
        }
    }

    fun pauseSession() {
        viewModelScope.launch {
            sessionManager.pause()
        }
    }

    fun resumeSession() {
        viewModelScope.launch {
            sessionManager.resume()
        }
    }

    fun stopSession() {
        viewModelScope.launch {
            sessionManager.stop()
        }
    }
}