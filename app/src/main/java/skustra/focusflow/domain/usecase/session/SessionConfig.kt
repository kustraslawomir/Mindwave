package skustra.focusflow.domain.usecase.session

import skustra.focusflow.BuildConfig
import skustra.focusflow.data.alias.Minute

class SessionConfig {
    companion object {

        fun tickInterval(): Long {
            return if (BuildConfig.DEBUG) 1000L else 1000L * 60L
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

        val DEFAULT_DURATION: Minute = availableDurations()[3]
        const val BREAK_DURATION: Minute = 5
        const val WORK_DURATION: Minute = 25
    }
}