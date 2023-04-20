package skustra.focusflow.data.timer


sealed class TimerState {
    data class InProgress(val progress: Progress) : TimerState()
    data class Paused(val progress: Progress) : TimerState()
    object Completed : TimerState()
    object Idle : TimerState()
}

