package skustra.focusflow.ui.composables.session

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import skustra.focusflow.data.SessionState
import skustra.focusflow.data.alias.Minute
import skustra.focusflow.domain.logs.AppLog
import skustra.focusflow.domain.usecase.session.SessionManager
import javax.inject.Inject

@HiltViewModel
class FocusSessionViewModel @Inject constructor(
    private val sessionManager: SessionManager,
) : ViewModel() {

    private val _sessionMutableStateFlow: MutableStateFlow<SessionState> =
        MutableStateFlow(SessionState.SessionIdle)

    fun sessionStateFlow(): StateFlow<SessionState> {
        return _sessionMutableStateFlow
    }

    init {
        viewModelScope.launch {
            sessionManager.sessionState().collect { state ->
                AppLog.sessionDebug(state)
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