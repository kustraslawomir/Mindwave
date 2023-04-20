package skustra.focusflow.domain.usecase

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.runBlocking

fun <T> SharedFlow<T>.getValueBlockedOrNull(): T? {
    var value: T?
    runBlocking(Dispatchers.Default) {
        value = when (this@getValueBlockedOrNull.replayCache.isEmpty()) {
            true -> null
            else -> this@getValueBlockedOrNull.firstOrNull()
        }
    }
    return value
}