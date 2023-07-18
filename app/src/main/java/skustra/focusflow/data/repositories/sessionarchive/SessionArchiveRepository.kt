package skustra.focusflow.data.repositories.sessionarchive

import androidx.annotation.WorkerThread
import kotlinx.coroutines.flow.Flow
import skustra.focusflow.data.database.dao.SessionArchiveDao
import skustra.focusflow.data.database.entity.SessionArchiveEntity
import skustra.focusflow.domain.ports.SessionArchiveRepositoryPort
import javax.inject.Inject

class SessionArchiveRepository @Inject constructor(
    private val archiveDao: SessionArchiveDao
) : SessionArchiveRepositoryPort {

    @WorkerThread
    override fun getAll(): List<SessionArchiveEntity> {
        return archiveDao.getAll()
    }

    @WorkerThread
    override fun getAllAsFlow(): Flow<List<SessionArchiveEntity>> {
        return archiveDao.getAllAsFlow()
    }

    @WorkerThread
    override fun getLastEntity(): SessionArchiveEntity? {
        return archiveDao.getLastEntity()
    }

    @WorkerThread
    override fun insert(archiveEntity: SessionArchiveEntity) {
        return archiveDao.insert(archiveEntity)
    }

    @WorkerThread
    override fun insert(archiveEntities: List<SessionArchiveEntity>) {
        archiveDao.insert(archiveEntities)
    }

    @WorkerThread
    override fun clearTable() {
        archiveDao.clearTable()
    }

    @WorkerThread
    override fun archiveIsEmpty(): Boolean {
        return archiveDao.countEntries() == 0
    }
}