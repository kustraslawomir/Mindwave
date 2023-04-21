package skustra.focusflow.domain.usecase.session

import skustra.focusflow.BuildConfig
import skustra.focusflow.data.alias.Minute
import skustra.focusflow.data.session.SessionPart
import skustra.focusflow.data.session.SessionPartType
import skustra.focusflow.data.session.Session
import timber.log.Timber

class SessionConfig {

    companion object {
        fun tickInterval(): Long {
            return if (BuildConfig.DEBUG) 1000L else 1000L * 60L
        }

        fun generate(
            duration: Minute = DEFAULT_DURATION,
            skipBreaks: Boolean = false
        ): Session {
            if (skipBreaks || duration <= WORK_DURATION) {
                return Session(
                    parts = listOf(
                        createSessionPart(duration, SessionPartType.Work)
                    ),
                    duration = duration
                )
            }

            val parts = mutableListOf<SessionPart>()
            for (i in duration downTo 0 step WORK_DURATION) {
                parts.add(createSessionPart(WORK_DURATION, SessionPartType.Work))
                if (i > 0) {
                    parts.add(createSessionPart(BREAK_DURATION, SessionPartType.Break))
                }
            }

            Timber.w("[SESSION_CREATION] Session for duration of: $duration -> \n${parts.map { "\n${it.sessionPartDuration} ${it.type}" }}")
            return Session(
                parts = parts,
                duration = duration
            )
        }

        fun availableDurations(): List<Minute> {
            return listOf(
                10,
                15,
                20,
                25,
                30,
                35,
                40,
                45,
                50,
                65,
                80,
                95,
                110,
                125,
                140,
                155,
                170,
                185,
                200,
                215,
                230,
                240
            )
        }

        const val DEFAULT_DURATION: Minute = 60
        private const val BREAK_DURATION: Minute = 5
        private const val WORK_DURATION: Minute = 25
        private fun createSessionPart(minute: Minute, type: SessionPartType) = SessionPart(
            type = type,
            sessionPartDuration = minute
        )
    }
}