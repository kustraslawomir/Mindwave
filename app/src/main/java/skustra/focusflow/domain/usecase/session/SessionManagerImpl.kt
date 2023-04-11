package skustra.focusflow.domain.usecase.session

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import skustra.focusflow.data.alias.Minute
import skustra.focusflow.data.SessionProgress
import skustra.focusflow.data.SessionState

class SessionManagerImpl : SessionManager {

    private val mutableSessionState = MutableSharedFlow<SessionState>()
    private val sessionState: SharedFlow<SessionState> = mutableSessionState.asSharedFlow()

    private var sessionDuration: Minute = 0
    private var currentSessionProgress: Minute = 0
    private var sessionPaused = false
    private var interval: Deferred<Unit>? = null

    override suspend fun createSession(sessionDuration: Minute, scope: CoroutineScope) {
        this.sessionDuration = sessionDuration
        this.currentSessionProgress = sessionDuration

        mutableSessionState.emit(
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
                    mutableSessionState.emit(SessionState.SessionCompleted)
                    return@launch
                }

                mutableSessionState.emit(
                    SessionState.SessionInProgress(
                        sessionProgress = getCurrentSessionProgress()
                    )
                )
            }
        }
    }

    override fun pauseSession() {
        sessionPaused = true
        mutableSessionState.tryEmit(
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
        mutableSessionState.tryEmit(SessionState.SessionIdle)
    }

    private fun getCurrentSessionProgress(): SessionProgress {
        return SessionProgress(
            minutesLeft = currentSessionProgress,
            sessionDuration = sessionDuration
        )
    }

    override fun getCurrentSessionState(): SharedFlow<SessionState> {
        return sessionState
    }

    private fun sessionEnded(): Boolean {
        return currentSessionProgress <= 0
    }

    private fun cancelInterval() {
        interval?.cancel()
    }
}