package skustra.focusflow.ui.composables.session

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import skustra.focusflow.data.SessionState
import skustra.focusflow.data.alias.Minute
import skustra.focusflow.domain.logs.AppLog
import skustra.focusflow.domain.usecase.resources.DrawableProvider
import skustra.focusflow.domain.usecase.session.SessionManager
import javax.inject.Inject

@HiltViewModel
class SessionViewModel @Inject constructor(
    private val sessionManager: SessionManager,
    val resourceManager: DrawableProvider
) : ViewModel() {

    private val _sessionMutableStateFlow: MutableStateFlow<SessionState> =
        MutableStateFlow(SessionState.SessionIdle)

    val sessionStateFlow: StateFlow<SessionState> = _sessionMutableStateFlow

    init {
        viewModelScope.launch {
            sessionManager.getCurrentSessionState().collect { state ->
                AppLog.debugSession(state)
                _sessionMutableStateFlow.emit(state)
            }
        }
    }

    fun createSession(minute: Minute) {
        viewModelScope.launch {
            sessionManager.createSession(minute, viewModelScope)
        }
    }

    fun pauseSession() {
        viewModelScope.launch {
            sessionManager.pauseSession()
        }
    }

    fun resumeSession() {
        viewModelScope.launch {
            sessionManager.resumeSession()
        }
    }

    fun stopSession() {
        viewModelScope.launch {
            sessionManager.stopSession()
        }
    }
}