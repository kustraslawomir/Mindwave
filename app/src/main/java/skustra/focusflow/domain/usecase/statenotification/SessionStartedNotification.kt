package skustra.focusflow.domain.usecase.statenotification

import skustra.focusflow.domain.usecase.vibrate.SingleVibrationUseCase
import javax.inject.Inject

class SessionStartedNotification @Inject constructor(
    private val vibrationUseCase: SingleVibrationUseCase
) : SessionPartCompletedNotification {

    override fun notifyUser() {
        vibrationUseCase.vibrate()
    }
}