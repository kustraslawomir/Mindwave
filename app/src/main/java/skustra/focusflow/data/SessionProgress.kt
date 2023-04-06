package skustra.focusflow.data

import skustra.focusflow.data.alias.Minute
import skustra.focusflow.domain.math.percentOf

data class SessionProgress(val minutesLeft: Minute, val sessionDuration: Minute) {

    fun percentageProgress(): Float {
        return sessionDuration percentOf minutesLeft
    }
}