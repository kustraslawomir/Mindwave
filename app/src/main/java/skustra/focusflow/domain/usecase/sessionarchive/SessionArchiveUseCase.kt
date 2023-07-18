package skustra.focusflow.domain.usecase.sessionarchive

import skustra.focusflow.domain.usecase.sessionarchive.archive.GetSessionArchiveUseCase
import skustra.focusflow.domain.usecase.sessionarchive.archive.SetSessionArchiveUseCase
import skustra.focusflow.domain.usecase.sessionarchive.statistics.GetStatisticsUseCase

data class SessionArchiveUseCase(
    val getSessionArchiveUseCase: GetSessionArchiveUseCase,
    val setSessionArchiveUseCase: SetSessionArchiveUseCase,
    val getStatisticsUseCase: GetStatisticsUseCase
)