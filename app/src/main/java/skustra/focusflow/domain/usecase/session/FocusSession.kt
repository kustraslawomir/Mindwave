package skustra.focusflow.domain.usecase.session

import kotlinx.coroutines.flow.SharedFlow
import skustra.focusflow.data.alias.Minute
import skustra.focusflow.data.SessionState

interface FocusSession {

    suspend fun startSession(sessionDuration: Minute)

    fun pauseSession()

    fun resumeSession()

    fun stopSession()

    fun sessionState(): SharedFlow<SessionState>
}