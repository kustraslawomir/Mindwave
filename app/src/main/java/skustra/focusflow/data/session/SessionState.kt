package skustra.focusflow.data.session

import skustra.focusflow.data.alias.Minute
import skustra.focusflow.data.exceptions.SessionAlreadyCompletedException
import skustra.focusflow.data.timer.TimerState
import skustra.focusflow.domain.usecase.session.SessionConfig
import java.util.UUID

data class SessionState(
    var currentTimerState: TimerState,
    var currentPartCounter: Int,
    val parts: List<SessionPart>
) {
    fun currentSessionPart(): SessionPart {
        return parts[currentPartCounter]
    }

    @Throws(SessionAlreadyCompletedException::class)
    fun activateTheNextPartOfTheSession() {
        if (currentPartCounter < parts.size - 1) {
            currentPartCounter += 1
        } else throw SessionAlreadyCompletedException()
    }

    fun deepCopy(): SessionState {
        return SessionState(
            currentTimerState = currentTimerState,
            currentPartCounter = currentPartCounter,
            parts = parts
        )
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
                    ),
                    SessionPart(
                        type = SessionPartType.Break,
                        sessionPartDuration = SessionConfig.defaultBreakDuration()
                    ),
                    SessionPart(
                        type = SessionPartType.Work,
                        sessionPartDuration = SessionConfig.defaultSessionDuration()
                    ),
                )
            )
        }
    }
}

sealed class SessionPartType {
    object Work : SessionPartType()
    object Break : SessionPartType()
}

data class SessionPart(val type: SessionPartType, val sessionPartDuration: Minute, val id : UUID = UUID.randomUUID())
