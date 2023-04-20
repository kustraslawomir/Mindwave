package skustra.focusflow.data.session

import skustra.focusflow.data.alias.Minute
import skustra.focusflow.data.timer.TimerState
import skustra.focusflow.domain.usecase.session.SessionConfig

data class SessionState(
    var currentTimerState: TimerState,
    val currentPartCounter: Int,
    val parts: List<SessionPart>
) {
    fun currentSessionPart(): SessionPart {
        return parts[currentPartCounter]
    }

    companion object {
        fun draft(): SessionState {
            return SessionState(
                currentTimerState = TimerState.Idle,
                currentPartCounter = 0,
                parts = listOf(
                    SessionPart(
                        type = SessionPartType.Work,
                        sessionPartDuration = SessionConfig.defaultSessionDuration()
                    )
                )
            )
        }
    }
}

sealed class SessionPartType {
    object Work : SessionPartType()
    object Break : SessionPartType()
}

data class SessionPart(val type: SessionPartType, val sessionPartDuration: Minute)
