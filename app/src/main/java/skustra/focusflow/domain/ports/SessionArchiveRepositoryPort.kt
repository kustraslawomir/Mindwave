package skustra.focusflow.domain.ports

import kotlinx.coroutines.flow.Flow
import skustra.focusflow.data.database.entity.SessionArchiveEntity

interface SessionArchiveRepositoryPort {
    fun getAll(): List<SessionArchiveEntity>
    fun getAllAsFlow(): Flow<List<SessionArchiveEntity>>
    fun getLastEntity(): SessionArchiveEntity?
    fun insert(archiveEntity: SessionArchiveEntity)
    fun insert(archiveEntities: List<SessionArchiveEntity>)
    fun clearTable()
    fun archiveIsEmpty() : Boolean
}