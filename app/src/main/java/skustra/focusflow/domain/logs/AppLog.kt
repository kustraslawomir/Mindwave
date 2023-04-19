package skustra.focusflow.domain.logs

import timber.log.Timber

object AppLog {

    fun debugSession(message: Any) {
        Timber.d("[SESSION] $message")
    }
}