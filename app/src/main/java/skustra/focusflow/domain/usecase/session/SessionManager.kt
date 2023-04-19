package skustra.focusflow.domain.usecase.session

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import skustra.focusflow.data.alias.Minute
import skustra.focusflow.data.SessionState

interface SessionManager {

    suspend fun createSession(sessionDuration: Minute, scope: CoroutineScope)

    fun pauseSession()

    fun resumeSession()

    fun stopSession()

    fun getCurrentSessionState(): Flow<SessionState>
}