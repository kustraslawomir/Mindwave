package skustra.focusflow.domain.logs

import skustra.focusflow.data.session.SessionState
import timber.log.Timber

object AppLog {

    fun debugSession(message: SessionState) {
        Timber.d("[SESSION] current part: ${message.currentPartCounter} of ${message.parts.size} | ${message.currentTimerState}")
    }
}