package skustra.focusflow.ui.composables.statistics

import androidx.annotation.WorkerThread
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
import skustra.focusflow.data.repository.SessionArchiveRepository
import skustra.focusflow.domain.usecase.session.SessionConfig
import skustra.focusflow.domain.utilities.dates.StatisticDateUtils
import skustra.focusflow.domain.utilities.dates.StatisticDateUtils.generateDates
import skustra.focusflow.domain.utilities.time.TimeUtils
import skustra.focusflow.ui.composables.statistics.chart.SessionArchiveEntry
import skustra.focusflow.ui.composables.statistics.chart.SessionArchiveEntryDataModel
import skustra.focusflow.ui.localization.LocalizationKey
import skustra.focusflow.ui.localization.LocalizationManager
import skustra.focusflow.ui.theme.CandyPink
import skustra.focusflow.ui.theme.Lavender
import skustra.focusflow.ui.theme.Lilac
import skustra.focusflow.ui.theme.PaleSeaBlue
import skustra.focusflow.ui.theme.PastelPink
import skustra.focusflow.ui.theme.ThisWeekCardColor
import skustra.focusflow.ui.theme.ThistlePink
import java.util.Calendar
import java.util.Date
import java.util.UUID
import javax.inject.Inject
import kotlin.random.Random


@HiltViewModel
class StatisticsViewModel @Inject constructor(
    private val sessionArchiveRepository: SessionArchiveRepository
) : ViewModel() {

    private val entryProducer = ChartEntryModelProducer()

    init {
        createEmptyStatistics()
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
        val statistics = sessionArchiveRepository.getDurationStatistics()
        return listOf(
            DurationUiModel(
                name = LocalizationManager.getText(LocalizationKey.CurrentWeekDurationSum),
                value = TimeUtils.formatMinutes(statistics.currentWeekDurationSum),
                color = ThisWeekCardColor
            ),
            DurationUiModel(
                name = LocalizationManager.getText(LocalizationKey.Last30DaysDuration),
                value = TimeUtils.formatMinutes(statistics.last30DaysDurationSum),
                color = Color.Black
            ),
            DurationUiModel(
                name = LocalizationManager.getText(LocalizationKey.TotalDuration),
                value = TimeUtils.formatMinutes(statistics.totalDuration),
                color = Color.Black
            ),
            DurationUiModel(
                name = LocalizationManager.getText(LocalizationKey.AverageDuration),
                value = TimeUtils.formatMinutes(statistics.countDurationAvg),
                color = Color.Black
            ),
            DurationUiModel(
                name = LocalizationManager.getText(LocalizationKey.CurrentStrike),
                value = statistics.currentStrike.toString(),
                color = Color.Black
            ),
            DurationUiModel(
                name = LocalizationManager.getText(LocalizationKey.LongestStrike),
                value = statistics.countLongestStrike.toString(),
                color = Color.Black
            ),
        )
    }

    @WorkerThread
    suspend fun getAxisValueMaxY(): Float {
        return withContext(viewModelScope.coroutineContext + Dispatchers.IO) { sessionArchiveRepository.getLongestDurationSessionArchive() }
    }

    private fun createEmptyStatistics() {
        viewModelScope.launch(Dispatchers.IO) {
            if (!sessionArchiveRepository.isEmpty()) {
                return@launch
            }

            val durations = SessionConfig.availableDurations()
            val fromDate = Calendar.getInstance()
            val dates = generateDates(
                fromDate = fromDate,
                toDate = Calendar.getInstance().apply {
                    time = fromDate.time
                    add(Calendar.MONTH, -1)
                }
            ).map { dayInterval ->
                val randomDuration = durations[Random.nextInt(0, durations.size - 1)]
                SessionArchiveEntity(
                    formattedDate = StatisticDateUtils.format(dayInterval),
                    sessionId = UUID.randomUUID().toString(),
                    minutes = if (BuildConfig.DEBUG) randomDuration else 0,
                    dateMs = dayInterval.time,
                    categoryId = SessionCategoryEntity.UnknownId
                )
            }
            sessionArchiveRepository.insert(dates)
        }
    }

    private fun fillStatisticsDateGap() {
        viewModelScope.launch(Dispatchers.IO) {
            val dates = generateDates(
                fromDate = Calendar.getInstance().apply {
                    add(Calendar.DATE, -1)
                },
                toDate = Calendar.getInstance().apply {
                    val lastEntityTime = sessionArchiveRepository.getLastEntity()?.dateMs
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
            sessionArchiveRepository.insert(dates)
        }
    }

    private fun listenToStatisticsChange() {
        viewModelScope.launch(Dispatchers.IO) {
            sessionArchiveRepository.getAllAsFlow().collect { data ->
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

