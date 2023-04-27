package skustra.focusflow.domain.usecase.session

import skustra.focusflow.data.model.alias.Minute
import skustra.focusflow.data.model.session.Session
import skustra.focusflow.data.model.session.SessionPart
import skustra.focusflow.data.model.session.SessionPartType
import timber.log.Timber

class SessionCreator {

    companion object {

        fun generate(
            sessionDuration: Minute = SessionConfig.DEFAULT_DURATION,
            skipBreaks: Boolean = false
        ): Session {
            val parts = mutableListOf<SessionPart>()

            val breaks = countBreaks(sessionDuration)
            if (skipBreaks || sessionDuration <= SessionConfig.WORK_DURATION || breaks == 0) {
                return Session(
                    parts = listOf(
                        createSessionPart(sessionDuration, SessionPartType.Work)
                    ),
                    duration = sessionDuration
                )
            }

            val workDuration = sessionDuration - (breaks * SessionConfig.BREAK_DURATION)
            val singleWorkIntervalDuration = workDuration / breaks
            for (i in workDuration downTo 0 step singleWorkIntervalDuration) {
                parts.add(createSessionPart(workDuration / (breaks + 1), SessionPartType.Work))
                if (i > breaks) {
                    parts.add(
                        createSessionPart(
                            SessionConfig.BREAK_DURATION,
                            SessionPartType.Break
                        )
                    )
                }
            }

            Timber.w("[SESSION_CREATION] Session for duration of: $sessionDuration -> \n${parts.map { "\n${it.sessionPartDuration} ${it.type}" }}")
            return Session(
                parts = parts,
                duration = sessionDuration
            )
        }

        private fun createSessionPart(minute: Minute, type: SessionPartType) = SessionPart(
            type = type,
            sessionPartDuration = minute
        )

        private fun countBreaks(duration: Minute): Int {
            return duration / SessionConfig.minimalDurationToIncludeBreaks()
        }
    }
}