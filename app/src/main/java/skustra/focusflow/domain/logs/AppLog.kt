package skustra.focusflow.domain.logs

import timber.log.Timber

object AppLog {

    fun sessionDebug(message: Any) {
        Timber.d("[SESSION] $message")
    }
}