package skustra.focusflow.data.timer

import skustra.focusflow.data.alias.Minute
import skustra.focusflow.domain.math.percentOf

data class Progress(val minutesLeft: Minute, val sessionDuration: Minute) {

    fun percentageProgress(): Float {
        return sessionDuration percentOf minutesLeft
    }
}