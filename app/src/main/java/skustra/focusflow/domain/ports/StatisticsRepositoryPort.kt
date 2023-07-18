package skustra.focusflow.domain.ports

import skustra.focusflow.data.model.statistics.SessionStatistics

interface StatisticsRepositoryPort {
    fun getSessionStatistics(): SessionStatistics
    fun getLongestSessionDurationOrDefault(): Float
}