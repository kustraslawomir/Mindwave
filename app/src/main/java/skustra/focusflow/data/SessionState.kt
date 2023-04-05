package skustra.focusflow.data


sealed class SessionState {

    abstract val sessionProgress: SessionProgress

    data class SessionInProgress(override val sessionProgress: SessionProgress) : SessionState()
    data class SessionPaused(override val sessionProgress: SessionProgress) : SessionState()
    data class SessionCompleted(
        override val sessionProgress: SessionProgress = SessionProgress(
            currentSessionProgress = 0,
            sessionDuration = 0
        )
    ) : SessionState()

    data class SessionIdle(
        override val sessionProgress: SessionProgress = SessionProgress(
            currentSessionProgress = 0,
            sessionDuration = 0
        )
    ) : SessionState()
}
