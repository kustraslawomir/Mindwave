package skustra.focusflow.domain.utilities.time

import skustra.focusflow.data.model.alias.Minute

class TimeUtils {
    companion object {
        fun formatMinutes(totalMinutes: Minute): String {
            var minutes = (totalMinutes % 60).toString()
            minutes = if (minutes.length == 1) "0$minutes" else minutes
            if (totalMinutes < 60) {
                return "$totalMinutes" + "m"
            }
            if (minutes == "00") {
                return (totalMinutes / 60).toString() + "h"
            }
            return (totalMinutes / 60).toString() + "h" + " " + minutes + "m"
        }
    }
}