package skustra.focusflow.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import skustra.focusflow.data.SessionState
import skustra.focusflow.data.alias.Minute
import skustra.focusflow.domain.logs.AppLog
import skustra.focusflow.domain.session.FocusSession
import skustra.focusflow.domain.session.FocusSessionImpl
import timber.log.Timber

class FocusSessionViewModel : ViewModel() {

    private val session: FocusSession = FocusSessionImpl()

    private val _sessionMutableStateFlow: MutableStateFlow<SessionState> =
        MutableStateFlow(SessionState.SessionIdle)

    fun sessionStateFlow(): StateFlow<SessionState> {
        return _sessionMutableStateFlow
    }

    init {
        viewModelScope.launch {
            session.sessionState().collect { state ->
                AppLog.sessionDebug(state)
                _sessionMutableStateFlow.emit(state)
            }
        }
    }

    fun startSession(minute: Minute) {
        viewModelScope.launch {
            session.startSession(minute)
        }
    }

    fun pauseSession() {
        viewModelScope.launch {
            session.pauseSession()
        }
    }

    fun resumeSession() {
        viewModelScope.launch {
            session.resumeSession()
        }
    }

    fun stopSession() {
        viewModelScope.launch {
            session.stopSession()
        }
    }
}