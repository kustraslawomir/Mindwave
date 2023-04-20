package skustra.focusflow.domain.logs

import timber.log.Timber

object AppLog {

    fun debugSession(message: Any) {
        val referenceValue = Integer.toHexString(System.identityHashCode(message))
        val className = message.javaClass.canonicalName
        val result = "$className@$referenceValue"
        Timber.d("[SESSION] $message | $result")
    }
}