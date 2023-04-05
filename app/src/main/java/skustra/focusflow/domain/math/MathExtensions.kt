package skustra.focusflow.domain.math

infix fun Int.percentOf(value: Int): Float {
    return if (this == 0) 0f
    else (value.toFloat() / this.toFloat()) * 100f
}