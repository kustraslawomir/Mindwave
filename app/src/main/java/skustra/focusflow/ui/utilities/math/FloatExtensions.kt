package skustra.focusflow.ui.utilities.math

import skustra.focusflow.ui.utilities.logs.AppLog
import kotlin.math.roundToInt

fun Float.round() : String {
    val result =  "${this.roundToInt()} m."
    AppLog.log("Round: $this to: $result")
    return result
}
