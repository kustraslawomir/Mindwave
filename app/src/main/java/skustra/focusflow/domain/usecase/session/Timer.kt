package skustra.focusflow.domain.usecase.session

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import skustra.focusflow.data.alias.Minute
import skustra.focusflow.data.TimerState

interface Timer {

    suspend fun run(sessionDuration: Minute, scope: CoroutineScope)

    fun pause()

    fun resume()

    fun stop()

    fun getCurrentTimerState(): Flow<TimerState>
}