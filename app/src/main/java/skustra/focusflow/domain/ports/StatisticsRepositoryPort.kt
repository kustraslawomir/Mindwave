package skustra.focusflow.domain.ports

import androidx.annotation.WorkerThread
import skustra.focusflow.data.model.statistics.SessionStatistics

interface StatisticsRepositoryPort {

    @WorkerThread
    fun getSessionStatistics(): SessionStatistics

    @WorkerThread
    fun getLongestSessionDurationOrDefault(): Float
}