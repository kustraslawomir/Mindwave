package skustra.focusflow.domain.usecase.sessionstate

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.StateFlow
import skustra.focusflow.data.model.alias.Minute
import skustra.focusflow.data.model.session.SessionPart
import skustra.focusflow.data.model.session.SessionPartType
import skustra.focusflow.data.model.timer.TimerState

interface SessionStateEmitter {

    fun start(sessionPart: SessionPart, scope: CoroutineScope)

    fun pause()

    fun resume()

    fun stop()

    fun getCurrentState(): StateFlow<TimerState>
}