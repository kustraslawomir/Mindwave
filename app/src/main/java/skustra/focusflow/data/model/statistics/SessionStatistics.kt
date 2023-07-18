package skustra.focusflow.data.model.statistics

import skustra.focusflow.data.model.alias.Minute

data class SessionStatistics(
    val totalDuration: Minute,
    val currentWeekDurationSum: Minute,
    val currentMonthDurationSum: Minute,
    val last30DaysDurationSum: Minute,
    val countDurationAvg: Minute,
    val countLongestStrike: Minute,
    val currentStrike: Minute
) {
    companion object {
        fun getDraft(): SessionStatistics {
            return SessionStatistics(
                totalDuration = 0,
                currentWeekDurationSum = 0,
                currentMonthDurationSum = 0,
                last30DaysDurationSum = 0,
                countDurationAvg = 0,
                countLongestStrike = 0,
                currentStrike = 0
            )
        }
    }
}