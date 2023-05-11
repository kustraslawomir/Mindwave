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
import java.util.UUID
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class StatisticsViewModel @Inject constructor(
    private val repository: SessionArchiveRepository
) : ViewModel() {

    private val entryProducer = ChartEntryModelProducer()

    fun getEntryProducer(): ChartEntryModelProducer {
        return entryProducer
    }

    init {
        viewModelScope.launch {
            repository.getAllAsFlow().collect { data ->
                Timber.d("Statistics size: ${data.size}")
                val producer = data
                    .groupBy { it.formattedDate }
                    .map { item ->
                        val durationSum = item.value.sumOf { it.minutes }
                        SessionArchiveEntry(
                            sessionArchiveEntryDataModel = SessionArchiveEntryDataModel(
                                summedDayDuration = durationSum,
                                date = item.key
                            ),
                            y = durationSum.toFloat(),
                            x = data.indexOf(item.value.first()).toFloat(),
                        )
                    }

                entryProducer.setEntries(producer)
            }
        }

        if (BuildConfig.DEBUG && repository.getAll().isEmpty()) {
            fakeAppData()
        }
    }

    private fun fakeAppData() {
        viewModelScope.launch {
            repository.clearTable()
            val durations = SessionConfig.availableDurations()
            val mockData = generateDates(Calendar.getInstance()).map { mockDate ->
                val randomDuration = durations[Random.nextInt(0, durations.size - 1)]
                SessionArchiveEntity(
                    formattedDate = StatisticDateUtils.format(mockDate),
                    sessionId = UUID.randomUUID().toString(),
                    minutes = randomDuration,
                    dateMs = mockDate.time
                )
            }
            repository.insert(mockData)
        }
    }
}