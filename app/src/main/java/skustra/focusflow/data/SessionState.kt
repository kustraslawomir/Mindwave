package skustra.focusflow.data


sealed class SessionState {
    data class SessionInProgress(val sessionProgress: SessionProgress) : SessionState()
    data class SessionPaused(val sessionProgress: SessionProgress) : SessionState()
    object SessionCompleted : SessionState()
    object SessionIdle : SessionState()
}
