package skustra.focusflow.domain.utilities.dates

import skustra.focusflow.ui.localization.LocalizationKey
import skustra.focusflow.ui.localization.LocalizationManager
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

object StatisticDateUtils {

    private var xAxisDateFormat = SimpleDateFormat("MM-dd", Locale.getDefault())

    val daysOfWeek = listOf(
        LocalizationManager.getText(LocalizationKey.Mon),
        LocalizationManager.getText(LocalizationKey.Tue),
        LocalizationManager.getText(LocalizationKey.Wed),
        LocalizationManager.getText(LocalizationKey.Thu),
        LocalizationManager.getText(LocalizationKey.Fri),
        LocalizationManager.getText(LocalizationKey.Sat),
        LocalizationManager.getText(LocalizationKey.Sun)
    )

    fun format(date : Date) : String {
        return xAxisDateFormat.format(date)
    }

    fun generateDates(fromDate: Calendar): List<Date> {
        val dates = mutableListOf<Date>()

        val toDate = Calendar.getInstance().apply {
            time = fromDate.time
            add(Calendar.YEAR, 1)
        }

        while (fromDate.time.before(toDate.time)) {
            fromDate.add(Calendar.DATE, 1)
            dates.add(fromDate.time)
            Timber.d("${dates.size}")
        }
        return dates
    }
}