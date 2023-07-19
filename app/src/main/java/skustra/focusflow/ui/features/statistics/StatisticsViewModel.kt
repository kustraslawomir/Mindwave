package skustra.focusflow.ui.features.statistics

import androidx.annotation.WorkerThread
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.patrykandpatrick.vico.core.entry.ChartEntryModelProducer
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import skustra.focusflow.BuildConfig
import skustra.focusflow.data.database.entity.SessionArchiveEntity
import skustra.focusflow.data.database.entity.SessionCategoryEntity
import skustra.focusflow.data.model.statistics.DurationUiModel
import skustra.focusflow.domain.usecase.session.SessionConfig
import skustra.focusflow.domain.usecase.sessionarchive.SessionArchiveUseCase
import skustra.focusflow.ui.utilities.dates.StatisticDateUtils
import skustra.focusflow.ui.utilities.dates.StatisticDateUtils.generateDates
import skustra.focusflow.ui.utilities.time.TimeUtils
import skustra.focusflow.ui.features.statistics.chart.SessionArchiveEntry
import skustra.focusflow.ui.features.statistics.chart.SessionArchiveEntryDataModel
import skustra.focusflow.ui.localization.LocalizationKey
import skustra.focusflow.ui.localization.LocalizationManager
import java.util.Calendar
import java.util.Date
import java.util.UUID
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class StatisticsViewModel @Inject constructor(
    private val sessionArchiveDataUseCase: SessionArchiveUseCase
) : ViewModel() {

    private val entryProducer = ChartEntryModelProducer()

    init {
        fillStatisticsDateGap()
        listenToStatisticsChange()
    }

    fun getEntryProducer(): ChartEntryModelProducer {
        return entryProducer
    }

    fun getEmptyDurationUiModelList(): List<DurationUiModel> {
        return emptyList()
    }

    @WorkerThread
    fun getDurationStatistics(): List<DurationUiModel> {
        val statistics = sessionArchiveDataUseCase.getStatisticsUseCase.getSessionStatistics()
        return listOf(
            DurationUiModel(
                name = LocalizationManager.getText(LocalizationKey.CurrentWeekDurationSum),
                value = TimeUtils.formatMinutes(statistics.currentWeekDurationSum)
            ),
            DurationUiModel(
                name = LocalizationManager.getText(LocalizationKey.Last30DaysDuration),
                value = TimeUtils.formatMinutes(statistics.last30DaysDurationSum)
            ),
            DurationUiModel(
                name = LocalizationManager.getText(LocalizationKey.TotalDuration),
                value = TimeUtils.formatMinutes(statistics.totalDuration)
            ),
            DurationUiModel(
                name = LocalizationManager.getText(LocalizationKey.AverageDuration),
                value = TimeUtils.formatMinutes(statistics.countDurationAvg)
            ),
            DurationUiModel(
                name = LocalizationManager.getText(LocalizationKey.CurrentStrike),
                value = statistics.currentStrike.toString()
            ),
            DurationUiModel(
                name = LocalizationManager.getText(LocalizationKey.LongestStrike),
                value = statistics.countLongestStrike.toString()
            ),
        )
    }

    @WorkerThread
    suspend fun getAxisValueMaxY(): Float {
        return withContext(viewModelScope.coroutineContext + Dispatchers.IO) { sessionArchiveDataUseCase.getStatisticsUseCase.getLongestSessionDurationOrDefault() }
    }

    private fun fillStatisticsDateGap() {
        viewModelScope.launch(Dispatchers.IO) {
            val dates = generateDates(
                fromDate = Calendar.getInstance().apply {
                    add(Calendar.DATE, -1)
                },
                toDate = Calendar.getInstance().apply {
                    val lastEntityTime =
                        sessionArchiveDataUseCase.getSessionArchiveUseCase.getLastEntity()?.dateMs
                    if (lastEntityTime != null) {
                        time = Date(lastEntityTime)
                    }
                }
            ).map { dayInterval ->
                SessionArchiveEntity(
                    formattedDate = StatisticDateUtils.format(dayInterval),
                    sessionId = UUID.randomUUID().toString(),
                    minutes = 0,
                    dateMs = dayInterval.time,
                    categoryId = SessionCategoryEntity.UnknownId
                )
            }
            sessionArchiveDataUseCase.setSessionArchiveUseCase.insert(dates)
        }
    }

    private fun listenToStatisticsChange() {
        viewModelScope.launch(Dispatchers.IO) {
            sessionArchiveDataUseCase.getSessionArchiveUseCase.getAllAsFlow().collect { data ->
                var entryXIndex = 0f
                val producer = data
                    .groupBy { it.formattedDate }
                    .map { item ->
                        val durationSum = item.value.sumOf { it.minutes }
                        val entry = SessionArchiveEntry(
                            sessionArchiveEntryDataModel = SessionArchiveEntryDataModel(
                                summedDayDuration = durationSum,
                                date = item.key
                            ),
                            x = entryXIndex,
                            y = durationSum.toFloat(),
                        )
                        entryXIndex += 1f
                        entry
                    }

                entryProducer.setEntries(producer)
            }
        }
    }
}

