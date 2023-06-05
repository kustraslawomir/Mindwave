package skustra.focusflow.domain.utilities.dates

import timber.log.Timber
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.OffsetDateTime
import java.util.Calendar
import java.util.Date
import java.util.Locale

object StatisticDateUtils {

    private var regularDateFormat = SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.getDefault())
    private var xAxisDateFormat = SimpleDateFormat("MM-dd", Locale.getDefault())

    fun format(date: Date): String = xAxisDateFormat.format(date)

    fun generateDates(fromDate : Calendar, toDate: Calendar): List<Date> {
        Timber.d("Generate dates between: ${fromDate.time} - ${toDate.time}")
        val dates = mutableListOf<Date>()
        while (toDate.time.before(fromDate.time)) {
            toDate.add(Calendar.DATE, 1)
            dates.add(toDate.time)
        }
        return dates
    }

    fun getDateMsWithoutTime(): Long {
        val localDate: LocalDate = LocalDate.now()
        return localDate
            .atStartOfDay()
            .toInstant(
                OffsetDateTime
                    .now()
                    .offset
            )
            .toEpochMilli()
    }

    fun formatDateMsToReadableDate(dateMs : Long): String {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = dateMs
        return regularDateFormat.format(calendar.time)
    }
}