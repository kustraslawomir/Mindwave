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
    private var periodicJob: Deferred<Unit>? = null

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
        periodicJob = scope.launchPeriodicAsync {
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
                        sessionProgress = getSessionProgress()
                    )
                )
            }
        }
    }

    override fun pauseSession() {
        sessionPaused = true
        mutableSessionState.tryEmit(
            SessionState.SessionPaused(
                sessionProgress = getSessionProgress()
            )
        )
    }

    private fun getSessionProgress(): SessionProgress {
        return SessionProgress(
            minutesLeft = currentSessionProgress,
            sessionDuration = sessionDuration
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

    override fun sessionState(): SharedFlow<SessionState> {
        return sessionState
    }

    private fun sessionEnded() = currentSessionProgress == 0

    private fun cancelInterval() {
        periodicJob?.cancel()
    }
}