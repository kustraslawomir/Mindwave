package skustra.focusflow.ui.extensions

import skustra.focusflow.data.model.alias.Minute
import skustra.focusflow.domain.utilities.logs.AppLog
import kotlin.math.roundToInt

fun Minute.toDisplayFormat() : String {
    return "$this m."
}

fun Float.toDisplayFormat() : String {
    val result =  "${this.roundToInt()} m."
    AppLog.log("Format: $this to: $result")
    return result
}
