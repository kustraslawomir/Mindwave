package skustra.focusflow.domain.ports

import androidx.annotation.WorkerThread
import kotlinx.coroutines.flow.Flow
import skustra.focusflow.data.database.entity.SessionArchiveEntity

interface SessionArchiveRepositoryPort {

    @WorkerThread
    fun getAll(): List<SessionArchiveEntity>

    @WorkerThread
    fun getAllAsFlow(): Flow<List<SessionArchiveEntity>>

    @WorkerThread
    fun getLastEntity(): SessionArchiveEntity?

    @WorkerThread
    fun insert(archiveEntity: SessionArchiveEntity)

    @WorkerThread
    fun insert(archiveEntities: List<SessionArchiveEntity>)

    @WorkerThread
    fun clearTable()

    @WorkerThread
    fun archiveIsEmpty() : Boolean
}