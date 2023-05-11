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

    fun format(date: Date): String = xAxisDateFormat.format(date)

    fun generateDates(): List<Date> {
        val fromDate = Calendar.getInstance()
        val dates = mutableListOf<Date>()

        val toDate = Calendar.getInstance().apply {
            time = fromDate.time
            add(Calendar.MONTH, -1)
        }

        while (toDate.time.before(fromDate.time)) {
            toDate.add(Calendar.DATE, 1)
            dates.add(toDate.time)
        }
        return dates
    }

    val daysOfWeek = listOf(
        LocalizationManager.getText(LocalizationKey.Mon),
        LocalizationManager.getText(LocalizationKey.Tue),
        LocalizationManager.getText(LocalizationKey.Wed),
        LocalizationManager.getText(LocalizationKey.Thu),
        LocalizationManager.getText(LocalizationKey.Fri),
        LocalizationManager.getText(LocalizationKey.Sat),
        LocalizationManager.getText(LocalizationKey.Sun)
    )
}