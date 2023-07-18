package skustra.focusflow.data.repositories.statistics

import skustra.focusflow.data.database.dao.SessionArchiveDao
import skustra.focusflow.data.model.alias.Minute
import skustra.focusflow.data.model.statistics.SessionStatistics
import skustra.focusflow.domain.ports.StatisticsRepositoryPort
import skustra.focusflow.domain.usecase.session.SessionConfig
import skustra.focusflow.domain.utilities.dates.StatisticDateUtils.formatDateMsToReadableDate
import skustra.focusflow.domain.utilities.dates.StatisticDateUtils.getDateMsWithoutTime
import timber.log.Timber
import java.util.Calendar
import javax.inject.Inject

class StatisticsRepository @Inject constructor(
    val archiveDao: SessionArchiveDao
) : StatisticsRepositoryPort {

    override fun getSessionStatistics(): SessionStatistics {
        return SessionStatistics(
            currentWeekDurationSum = currentWeekDurationSum(),
            currentMonthDurationSum = currentMonthDurationSum(),
            totalDuration = totalDurationSum(),
            last30DaysDurationSum = last30DaysDurationSum(),
            countDurationAvg = countDurationAvg(),
            countLongestStrike = countLongestStrike(),
            currentStrike = getCurrentStrike()
        )
    }

    override fun getLongestSessionDurationOrDefault(): Float {
        val entities = archiveDao.getAll()
        if (entities.isEmpty()) {
            return SessionConfig.SESSION_MAX_DURATION_LIMIT.toFloat()
        }
        var maxDuration = 0f
        entities.groupBy { it.formattedDate }
            .forEach { entityGroup ->
                val durationSum = entityGroup.value.sumOf { group -> group.minutes }.toFloat()
                if (maxDuration < durationSum) {
                    maxDuration = durationSum
                }
            }

        if (maxDuration > SessionConfig.SESSION_MAX_DURATION_LIMIT) {
            return maxDuration
        }
        return SessionConfig.SESSION_MAX_DURATION_LIMIT.toFloat()
    }

    private fun totalDurationSum(): Minute = archiveDao.totalDurationSum()

    private fun currentWeekDurationSum(): Minute {
        val calendar = Calendar.getInstance().apply {
            clear()
            timeInMillis = getDateMsWithoutTime()
        }
        while (calendar[Calendar.DAY_OF_WEEK] > calendar.firstDayOfWeek) {
            calendar.add(Calendar.DATE, -1)
        }

        return sumDurationBetween(
            betweenDateMs = calendar.timeInMillis,
            andDateMs = System.currentTimeMillis()
        )
    }

    private fun currentMonthDurationSum(): Minute {
        val calendar = Calendar.getInstance().apply {
            clear()
            timeInMillis = getDateMsWithoutTime()
        }
        while (calendar[Calendar.DATE] > 1) {
            calendar.add(Calendar.DATE, -1)
        }

        return sumDurationBetween(
            betweenDateMs = calendar.timeInMillis,
            andDateMs = System.currentTimeMillis()
        )
    }

    private fun last30DaysDurationSum(): Minute {
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.DATE, -30)
        return sumDurationBetween(
            betweenDateMs = calendar.timeInMillis,
            andDateMs = System.currentTimeMillis()
        )
    }

    private fun countDurationAvg(): Minute {
        val durationGroupedByDay = getDurationsGroupedByDay()
        if (durationGroupedByDay.isEmpty()) {
            return 0
        }
        return durationGroupedByDay
            .average()
            .toInt()
    }

    private fun countLongestStrike(): Int {
        val durationGroupedByDay = getDurationsGroupedByDay()
        if (durationGroupedByDay.isEmpty()) {
            return 0
        }
        var strikeCount = 0
        var maxStrike = 0
        durationGroupedByDay.forEach { duration ->
            if (duration > 0) {
                strikeCount += 1
                if (strikeCount > maxStrike) {
                    maxStrike = strikeCount
                }
            } else {
                strikeCount = 0
            }
        }

        return maxStrike
    }

    private fun getCurrentStrike(): Int {
        val durationGroupedByDay = getDurationsGroupedByDay()
        if (durationGroupedByDay.isEmpty()) {
            return 0
        }
        var strikeCount = 0
        durationGroupedByDay.forEach { duration ->
            Timber.d("current strike? $duration")
            if (duration > 0) {
                strikeCount += 1
            } else {
                return strikeCount
            }
        }

        return strikeCount
    }

    private fun getDurationsGroupedByDay(): MutableList<Int> {
        val durationGroupedByDay = mutableListOf<Int>()

        val lastEntityWithNonEmptyDuration = archiveDao
            .getOldestEntityWithNonEmptyDuration() ?: return durationGroupedByDay

        archiveDao.getBetween(
            betweenDateMs = lastEntityWithNonEmptyDuration.dateMs,
            andDateMs = System.currentTimeMillis()
        ).groupBy { entity ->
            entity.formattedDate
        }.forEach { (_, group) ->
            durationGroupedByDay.add(group.sumOf { entity -> entity.minutes })
        }
        return durationGroupedByDay
    }

    private fun sumDurationBetween(
        betweenDateMs: Long,
        andDateMs: Long
    ): Int {
        Timber.w(
            "Sum duration between: ${formatDateMsToReadableDate(betweenDateMs)} ($betweenDateMs) and ${
                formatDateMsToReadableDate(
                    andDateMs
                )
            } ($andDateMs)"
        )
        return archiveDao.sumDurationBetween(
            betweenDateMs = betweenDateMs,
            andDateMs = andDateMs
        )
    }
}