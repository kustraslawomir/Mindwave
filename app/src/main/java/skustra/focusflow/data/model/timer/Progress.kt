package skustra.focusflow.data.model.timer

import skustra.focusflow.data.model.alias.Minute
import skustra.focusflow.domain.utilities.math.percentOf

data class Progress(val minutesLeft: Minute, val sessionDuration: Minute) {

    fun percentageProgress(): Float {
        return sessionDuration percentOf minutesLeft
    }
}