package skustra.focusflow.domain.usecase.session

import skustra.focusflow.BuildConfig
import skustra.focusflow.data.alias.Minute
import java.time.LocalDateTime
import java.time.ZoneOffset

class SessionConfig {
    companion object {

        fun tickInterval(): Long {
            return if (BuildConfig.DEBUG) 1000L else 1000L * 60L
        }

        fun minimalDurationToIncludeBreaks() = 30

        fun availableDurations(): List<Minute> {
            val mutableList = mutableListOf(
                10,
                15,
                20,
                25
            )
            for (i in 30..SESSION_MAX_DURATION_LIMIT step SESSION_DURATION_STEP) {
                mutableList.add(i)
            }

            val test : LocalDateTime = LocalDateTime.now()
            test.atOffset(ZoneOffset.UTC)

            return mutableList
        }

        val DEFAULT_DURATION: Minute = availableDurations()[3]

        const val WORK_DURATION: Minute = 25
        const val BREAK_DURATION: Minute = 5

        private const val SESSION_DURATION_STEP: Minute = 15
        private const val SESSION_MAX_DURATION_LIMIT: Minute = 240
    }
}