package skustra.focusflow.data.model.exceptions

import skustra.focusflow.data.model.alias.Minute
import java.util.concurrent.TimeUnit

fun Minute.toMs(): Long {
    return TimeUnit.MINUTES.toMillis(this.toLong())
}
