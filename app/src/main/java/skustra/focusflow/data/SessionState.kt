package skustra.focusflow.data


sealed class SessionState {

    data class SessionInProgress(val sessionProgress: SessionProgress) : SessionState()
    object SessionPaused : SessionState()
    object SessionResumed : SessionState()
    object SessionCompleted : SessionState()
    object SessionIdle : SessionState()
}
