package skustra.focusflow.domain.usecase.sessionarchive.archive

import androidx.annotation.WorkerThread
import skustra.focusflow.domain.ports.SessionArchiveRepositoryPort

data class GetSessionArchiveUseCase(
    val repository: SessionArchiveRepositoryPort
) {
    @WorkerThread
    fun getAll() = repository.getAll()

    @WorkerThread
    fun getAllAsFlow() = repository.getAllAsFlow()

    @WorkerThread
    fun getLastEntity() = repository.getLastEntity()

    @WorkerThread
    fun archiveIsEmpty() = repository.archiveIsEmpty()
}

