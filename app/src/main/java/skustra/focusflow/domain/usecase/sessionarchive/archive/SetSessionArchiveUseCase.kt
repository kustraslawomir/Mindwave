package skustra.focusflow.domain.usecase.sessionarchive.archive

import androidx.annotation.WorkerThread
import skustra.focusflow.data.database.entity.SessionArchiveEntity
import skustra.focusflow.domain.ports.SessionArchiveRepositoryPort


data class SetSessionArchiveUseCase(
    val repository: SessionArchiveRepositoryPort
) {
    @WorkerThread
    fun insert(archiveEntity: SessionArchiveEntity) = repository.insert(archiveEntity)

    @WorkerThread
    fun insert(archiveEntities: List<SessionArchiveEntity>) = repository.insert(archiveEntities)

    @WorkerThread
    fun clearTable() = repository.clearTable()
}