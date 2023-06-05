package skustra.focusflow.data.repository

import kotlinx.coroutines.flow.Flow
import skustra.focusflow.data.database.dao.SessionArchiveDao
import skustra.focusflow.data.database.entity.SessionArchiveEntity
import skustra.focusflow.data.model.alias.Minute
import skustra.focusflow.domain.usecase.session.SessionConfig
import skustra.focusflow.domain.utilities.dates.StatisticDateUtils.formatDateMsToReadableDate
import skustra.focusflow.domain.utilities.dates.StatisticDateUtils.getDateMsWithoutTime
import timber.log.Timber
import java.util.Calendar
import javax.inject.Inject


class SessionArchiveRepository @Inject constructor(private val archiveDao: SessionArchiveDao) {


    fun getAll(): List<SessionArchiveEntity> = archiveDao.getAll()

    fun getAllAsFlow(): Flow<List<SessionArchiveEntity>> = archiveDao.getAllAsFlow()

    fun getLastEntity(): SessionArchiveEntity? = archiveDao.getLastEntity()

    fun insert(archiveEntity: SessionArchiveEntity) = archiveDao.insert(archiveEntity)

    fun insert(archiveEntities: List<SessionArchiveEntity>) = archiveDao.insert(archiveEntities)

    fun clearTable() = archiveDao.clearTable()

    fun isEmpty() = archiveDao.countEntries() == 0

    fun totalDurationSum(): Minute = archiveDao.totalDurationSum()

    fun getLongestDurationSessionArchive(): Float {
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


    fun currentWeekDurationSum(): Minute {
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

    fun currentMonthDurationSum(): Minute {
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

    fun last30DaysDurationSum(): Minute {
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.DATE, -30)
        return sumDurationBetween(
            betweenDateMs = calendar.timeInMillis,
            andDateMs = System.currentTimeMillis()
        )
    }

    fun countLongestStrike(): Int {
        val lastEntityWithNonEmptyDuration = archiveDao
            .getOldestEntityWithNonEmptyDuration() ?: return 0

        return 0
    }

    fun getCurrentStrike(): Int {
        val lastEntityWithNonEmptyDuration = archiveDao
            .getOldestEntityWithNonEmptyDuration() ?: return 0

        return 0
    }

    fun countDurationAvg(): Minute {

        val lastEntityWithNonEmptyDuration = archiveDao
            .getOldestEntityWithNonEmptyDuration() ?: return 0
        val durationGroupedByDay = mutableListOf<Int>()

        archiveDao.getBetween(
            betweenDateMs = lastEntityWithNonEmptyDuration.dateMs,
            andDateMs = System.currentTimeMillis()
        ).groupBy { entity ->
            entity.formattedDate
        }.forEach { (_, group) ->
            durationGroupedByDay.add(group.sumOf { entity -> entity.minutes })
        }
        
        return durationGroupedByDay
            .average()
            .toInt()
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