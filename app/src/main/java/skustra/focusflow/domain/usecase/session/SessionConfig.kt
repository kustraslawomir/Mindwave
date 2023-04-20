package skustra.focusflow.domain.usecase.session

import skustra.focusflow.BuildConfig
import skustra.focusflow.data.alias.Minute
import skustra.focusflow.data.alias.Second

class SessionConfig {

    companion object {

        fun tickInterval(): Long {
            return if (BuildConfig.DEBUG) 1000L else 1000L * 60L
        }

        val predefinedSessionDuration = listOf<Minute>(
            25,
            45,
            60
        )

        fun defaultSessionDuration() = predefinedSessionDuration
            .first()

        fun defaultBreakDuration() : Minute = 5
    }
}