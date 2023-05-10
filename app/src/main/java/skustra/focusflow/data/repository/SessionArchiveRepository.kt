package skustra.focusflow.data.repository

import kotlinx.coroutines.flow.Flow
import skustra.focusflow.data.database.dao.SessionArchiveDao
import skustra.focusflow.data.database.entity.SessionArchiveEntity
import javax.inject.Inject

class SessionArchiveRepository @Inject constructor(private val archiveDao: SessionArchiveDao) {

    fun getAll(): List<SessionArchiveEntity> = archiveDao.getAll()
    fun getAllAsFlow(): Flow<List<SessionArchiveEntity>> = archiveDao.getAllAsFlow()
    fun insert(archiveEntity: SessionArchiveEntity) = archiveDao.insert(archiveEntity)
}