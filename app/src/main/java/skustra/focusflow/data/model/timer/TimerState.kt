package skustra.focusflow.data.model.timer

import skustra.focusflow.data.model.session.SessionPartType


sealed class TimerState {
    data class InProgress(val progress: Progress) : TimerState()
    data class Paused(val progress: Progress) : TimerState()
    data class Completed(val type: SessionPartType) : TimerState()
    object Idle : TimerState()
}

