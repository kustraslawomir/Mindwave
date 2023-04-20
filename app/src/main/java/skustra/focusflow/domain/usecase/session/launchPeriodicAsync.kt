package skustra.focusflow.domain.usecase.session

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive

fun CoroutineScope.launchPeriodicAsync(
    onTick: () -> Unit
) = this.async {
    while (isActive) {
        delay(SessionConfig.tickInterval())
        onTick()
    }
}