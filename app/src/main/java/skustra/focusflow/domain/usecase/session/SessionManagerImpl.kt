package skustra.focusflow.domain.usecase.session

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import skustra.focusflow.data.alias.Minute
import skustra.focusflow.data.SessionProgress
import skustra.focusflow.data.SessionState

class SessionManagerImpl : SessionManager {

    private val mutableSessionStateFlow = MutableStateFlow<SessionState>(SessionState.SessionIdle)
    private val sessionStateStateFlow: StateFlow<SessionState> = mutableSessionStateFlow

    private var sessionDuration: Minute = 0
    private var currentSessionProgress: Minute = 0
    private var sessionPaused = false
    private var interval: Deferred<Unit>? = null

    override suspend fun createSession(sessionDuration: Minute, scope: CoroutineScope) {
        this.sessionDuration = sessionDuration
        this.currentSessionProgress = sessionDuration

        mutableSessionStateFlow.emit(
            SessionState.SessionInProgress(
                sessionProgress = SessionProgress(
                    sessionDuration = sessionDuration,
                    minutesLeft = currentSessionProgress
                )
            )
        )

        cancelInterval()
        runInterval(scope)
    }

    private fun runInterval(scope: CoroutineScope) {
        interval = scope.launchPeriodicAsync {
            scope.launch {
                if (sessionPaused) {
                    return@launch
                }

                currentSessionProgress -= 1
                if (sessionEnded()) {
                    cancelInterval()
                    mutableSessionStateFlow.emit(SessionState.SessionCompleted)
                    return@launch
                }

                mutableSessionStateFlow.emit(
                    SessionState.SessionInProgress(
                        sessionProgress = getCurrentSessionProgress()
                    )
                )
            }
        }
    }

    override fun pauseSession() {
        sessionPaused = true
        mutableSessionStateFlow.tryEmit(
            SessionState.SessionPaused(
                sessionProgress = getCurrentSessionProgress()
            )
        )
    }

    override fun resumeSession() {
        sessionPaused = false
    }

    override fun stopSession() {
        cancelInterval()
        sessionPaused = false
        mutableSessionStateFlow.tryEmit(SessionState.SessionIdle)
    }

    private fun getCurrentSessionProgress(): SessionProgress {
        return SessionProgress(
            minutesLeft = currentSessionProgress,
            sessionDuration = sessionDuration
        )
    }

    override fun getCurrentSessionState(): Flow<SessionState> {
        return sessionStateStateFlow
    }

    private fun sessionEnded(): Boolean {
        return currentSessionProgress <= 0
    }

    private fun cancelInterval() {
        interval?.cancel()
    }
}