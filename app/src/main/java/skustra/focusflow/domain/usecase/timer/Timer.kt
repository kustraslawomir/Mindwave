package skustra.focusflow.domain.usecase.timer

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import skustra.focusflow.data.model.alias.Minute
import skustra.focusflow.data.model.timer.TimerState

interface Timer {

    suspend fun start(sessionDuration: Minute, scope: CoroutineScope)

    fun pause()

    fun resume()

    fun stop()

    fun getCurrentTimerState(): StateFlow<TimerState>
}