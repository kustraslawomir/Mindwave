package skustra.focusflow.ui.composables.session

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.flow.lastOrNull
import kotlinx.coroutines.launch
import skustra.focusflow.data.alias.Minute
import skustra.focusflow.data.session.SessionState
import skustra.focusflow.domain.logs.AppLog
import skustra.focusflow.domain.usecase.getValueBlockedOrNull
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

    init {
        viewModelScope.launch {
            timer.getCurrentTimerState().collect { state ->
                val updatedState =
                    sessionStateFlow.getValueBlockedOrNull() ?: SessionState.draft().apply {
                        currentTimerState = state
                    }

                _sessionMutableStateFlow.emit(updatedState)
                AppLog.debugSession(updatedState)
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