package skustra.focusflow.data.repository

import kotlinx.coroutines.flow.Flow
import skustra.focusflow.data.database.dao.SessionArchiveDao
import skustra.focusflow.data.database.entity.SessionArchiveEntity
import skustra.focusflow.data.model.alias.Minute
import skustra.focusflow.domain.usecase.session.SessionConfig
import timber.log.Timber
import javax.inject.Inject

class SessionArchiveRepository @Inject constructor(private val archiveDao: SessionArchiveDao) {

    fun getAll(): List<SessionArchiveEntity> = archiveDao.getAll()
    fun getAllAsFlow(): Flow<List<SessionArchiveEntity>> = archiveDao.getAllAsFlow()
    fun getLastEntity(): SessionArchiveEntity? = archiveDao.getLastEntity()
    fun insert(archiveEntity: SessionArchiveEntity) = archiveDao.insert(archiveEntity)
    fun insert(archiveEntities: List<SessionArchiveEntity>) = archiveDao.insert(archiveEntities)
    fun clearTable() = archiveDao.clearTable()
    fun isEmpty() = archiveDao.countEntries() == 0

    fun getLongestDurationSessionArchive(): Float {
        val entities = archiveDao.getAll()
        if (entities.isEmpty()) {
            return SessionConfig.SESSION_MAX_DURATION_LIMIT.toFloat()
        }
        var maxDuration = 0f
        entities.groupBy { it.formattedDate }
            .forEach { it ->
                val durationSum = it.value.sumOf { it.minutes }.toFloat()
                if (maxDuration < durationSum) {
                    maxDuration = durationSum
                }
            }

        if (maxDuration > SessionConfig.SESSION_MAX_DURATION_LIMIT) {
            return maxDuration
        }
        return SessionConfig.SESSION_MAX_DURATION_LIMIT.toFloat()
    }
}