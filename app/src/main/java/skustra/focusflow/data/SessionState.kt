package skustra.focusflow.data

import skustra.focusflow.data.alias.Minute
import java.util.UUID

sealed class SessionState {

    data class SessionInProgress(val sessionProgress: SessionProgress) : SessionState()
    object SessionPaused : SessionState()
    object SessionResumed : SessionState()
    data class SessionCompleted(val sessionId : UUID, val duration : Minute) : SessionState()
    object SessionIdle : SessionState()
}
