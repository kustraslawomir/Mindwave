package skustra.focusflow.data.repositories.sessionarchive

import androidx.annotation.WorkerThread
import kotlinx.coroutines.flow.Flow
import skustra.focusflow.data.database.dao.SessionArchiveDao
import skustra.focusflow.data.database.entity.SessionArchiveEntity
import javax.inject.Inject


class SessionArchiveRepository @Inject constructor(private val archiveDao: SessionArchiveDao) {

    @WorkerThread
    fun getAll(): List<SessionArchiveEntity> = archiveDao.getAll()

    @WorkerThread
    fun getAllAsFlow(): Flow<List<SessionArchiveEntity>> = archiveDao.getAllAsFlow()

    @WorkerThread
    fun getLastEntity(): SessionArchiveEntity? = archiveDao.getLastEntity()

    @WorkerThread
    fun insert(archiveEntity: SessionArchiveEntity) = archiveDao.insert(archiveEntity)

    @WorkerThread
    fun insert(archiveEntities: List<SessionArchiveEntity>) = archiveDao.insert(archiveEntities)

    @WorkerThread
    fun clearTable() = archiveDao.clearTable()

    @WorkerThread
    fun isEmpty() = archiveDao.countEntries() == 0
}