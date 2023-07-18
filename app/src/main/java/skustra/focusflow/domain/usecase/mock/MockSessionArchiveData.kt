package skustra.focusflow.domain.usecase.mock

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import skustra.focusflow.BuildConfig
import skustra.focusflow.data.database.entity.SessionArchiveEntity
import skustra.focusflow.data.database.entity.SessionCategoryEntity
import skustra.focusflow.domain.usecase.session.SessionConfig
import skustra.focusflow.domain.usecase.sessionarchive.SessionArchiveUseCase
import skustra.focusflow.ui.utilities.dates.StatisticDateUtils
import skustra.focusflow.ui.utilities.dates.StatisticDateUtils.generateDates
import timber.log.Timber
import java.util.Calendar
import java.util.UUID
import javax.inject.Inject
import kotlin.random.Random

class MockSessionArchiveData @Inject constructor(private val sessionArchiveDataUseCase: SessionArchiveUseCase) {

    fun fillDatabaseWithMockData(scope: CoroutineScope) {
        if (!BuildConfig.DEBUG) {
            throw IllegalStateException("Mocking the session archive data is only available on debug mode.")
        }

        scope.launch(Dispatchers.IO) {
            if (!sessionArchiveDataUseCase.getSessionArchiveUseCase.archiveIsEmpty()) {
                Timber.d("Data has been already mocked.")
                return@launch
            }

            val durations = SessionConfig.availableDurations()
            val fromDate = Calendar.getInstance()
            val dates = generateDates(fromDate = fromDate, toDate = Calendar.getInstance().apply {
                time = fromDate.time
                add(Calendar.MONTH, -1)
            }).map { dayInterval ->
                val randomDuration = durations[Random.nextInt(0, durations.size - 1)]
                SessionArchiveEntity(
                    formattedDate = StatisticDateUtils.format(dayInterval),
                    sessionId = UUID.randomUUID().toString(),
                    minutes = if (BuildConfig.DEBUG) randomDuration else 0,
                    dateMs = dayInterval.time,
                    categoryId = SessionCategoryEntity.UnknownId
                )
            }
            sessionArchiveDataUseCase.setSessionArchiveUseCase.insert(dates)
        }
    }
}