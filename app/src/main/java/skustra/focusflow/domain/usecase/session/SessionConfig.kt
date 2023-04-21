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
                45,
                60,
                75,
                90,
                105,
                120,
                135,
                150,
                165,
                180,
                195,
                210,
                225,
                240
            )
        }

        val DEFAULT_DURATION: Minute = availableDurations()[3]
        const val BREAK_DURATION: Minute = 5
        const val WORK_DURATION: Minute = 25
    }
}