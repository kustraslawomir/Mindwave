package skustra.focusflow.domain.usecase.sessionarchive.statistics

import androidx.annotation.WorkerThread
import skustra.focusflow.domain.ports.StatisticsRepositoryPort

data class GetStatisticsUseCase(
    val repository: StatisticsRepositoryPort
) {
    @WorkerThread
    fun getSessionStatistics() = repository.getSessionStatistics()

    @WorkerThread
    fun getLongestSessionDurationOrDefault() = repository.getLongestSessionDurationOrDefault()
}