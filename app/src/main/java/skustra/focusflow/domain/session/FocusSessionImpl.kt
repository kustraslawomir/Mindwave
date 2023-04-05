package skustra.focusflow.domain.session

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import skustra.focusflow.data.alias.Minute
import skustra.focusflow.data.SessionProgress
import skustra.focusflow.data.SessionState

class FocusSessionImpl : FocusSession {

    private val mutableSessionState = MutableSharedFlow<SessionState>()
    private val sessionState: SharedFlow<SessionState> = mutableSessionState.asSharedFlow()

    private var currentSessionProgress: Minute = 0
    private var sessionPaused = false

    override suspend fun startSession(sessionDuration: Minute) {
        this.currentSessionProgress = sessionDuration
        mutableSessionState.emit(
            SessionState.SessionInProgress(
                sessionProgress = SessionProgress(
                    sessionDuration = sessionDuration,
                    currentSessionProgress = currentSessionProgress
                )
            )
        )

        while (true) {
            delay(SessionConfig.tickInterval())
            if (sessionPaused) {
                return
            }

            currentSessionProgress -= 1
            if (sessionEnded()) {
                mutableSessionState.emit(SessionState.SessionCompleted)
                return
            }

            mutableSessionState.emit(
                SessionState.SessionInProgress(
                    sessionProgress = SessionProgress(
                        currentSessionProgress = currentSessionProgress,
                        sessionDuration = sessionDuration
                    )
                )
            )
        }
    }

    override fun pauseSession() {
        sessionPaused = true
        mutableSessionState.tryEmit(SessionState.SessionPaused)
    }

    override fun resumeSession() {
        sessionPaused = false
        mutableSessionState.tryEmit(SessionState.SessionResumed)
    }

    override fun stopSession() {
        sessionPaused = false
        mutableSessionState.tryEmit(SessionState.SessionIdle)
    }

    override fun sessionState(): SharedFlow<SessionState> {
        return sessionState
    }

    private fun sessionEnded() = currentSessionProgress == 0
}