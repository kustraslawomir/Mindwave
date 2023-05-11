package skustra.focusflow.ui.extensions

import skustra.focusflow.data.model.alias.Minute
import kotlin.math.roundToInt

fun Minute.toDisplayFormat() : String {
    return "$this m."
}

fun Float.toDisplayFormat() : String {
    return "${this.roundToInt()} m."
}
