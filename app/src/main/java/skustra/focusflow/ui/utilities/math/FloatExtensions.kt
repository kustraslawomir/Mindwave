package skustra.focusflow.ui.utilities.math

import kotlin.math.roundToInt

fun Float.convertToShortMinute(): String {
    return "${roundToInt()} m."
}