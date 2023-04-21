package skustra.focusflow.domain.logs

import skustra.focusflow.data.session.Session
import timber.log.Timber

object AppLog {

    fun debugSession(message: Session) {
        Timber.d("[SESSION] current part: ${message.currentPartCounter} of ${message.parts.size} | ${message.currentTimerState}")
    }
}