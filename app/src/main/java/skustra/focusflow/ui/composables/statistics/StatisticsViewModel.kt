package skustra.focusflow.ui.composables.statistics

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.patrykandpatrick.vico.core.entry.ChartEntryModelProducer
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import skustra.focusflow.BuildConfig
import skustra.focusflow.data.database.entity.SessionArchiveEntity
import skustra.focusflow.data.repository.SessionArchiveRepository
import skustra.focusflow.domain.usecase.session.SessionConfig
import skustra.focusflow.domain.utilities.dates.StatisticDateUtils
import skustra.focusflow.domain.utilities.dates.StatisticDateUtils.generateDates
import timber.log.Timber
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

    fun getEntryProducer(): ChartEntryModelProducer {
        return entryProducer
    }

    init {
        createEmptyStatistics()
        fillStatisticsDateGap()
        listenToStatisticsChange()
    }

    private fun createEmptyStatistics() {
        viewModelScope.launch {
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
                    dateMs = dayInterval.time
                )
            }
            sessionArchiveRepository.insert(dates)
        }
    }

    private fun fillStatisticsDateGap() {
        viewModelScope.launch {
            val dates = generateDates(
                fromDate = Calendar.getInstance(),
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
                    dateMs = dayInterval.time
                )
            }
            sessionArchiveRepository.insert(dates)
        }
    }

    private fun listenToStatisticsChange() {
        viewModelScope.launch {
            sessionArchiveRepository.getAllAsFlow().collect { data ->
                Timber.d("Statistics size: ${data.size}")
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

    fun getAxisValueMaxY(): Float {
        return sessionArchiveRepository.getLongestDurationSessionArchive()
    }
}