package skustra.focusflow.ui.utilities.math

infix fun Int.percentOf(value: Int): Float {
    return if (this == 0) 0f
    else (value.toFloat() / this.toFloat()) * 100f
}