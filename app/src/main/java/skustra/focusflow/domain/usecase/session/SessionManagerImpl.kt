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

    override suspend fun startSession(sessionDuration: Minute) {
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
                    sessionProgress = getSessionProgress()
                )
            )
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
        sessionPaused = false
        mutableSessionState.tryEmit(SessionState.SessionIdle)
    }

    override fun sessionState(): SharedFlow<SessionState> {
        return sessionState
    }

    private fun sessionEnded() = currentSessionProgress == 0
}