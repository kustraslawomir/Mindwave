package skustra.focusflow.domain.usecase.interval

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import skustra.focusflow.domain.usecase.session.SessionConfig

fun CoroutineScope.launchPeriodicAsync(
    onTick: () -> Unit
) = this.async {
    while (isActive) {
        delay(SessionConfig.tickInterval())
        onTick()
    }
}