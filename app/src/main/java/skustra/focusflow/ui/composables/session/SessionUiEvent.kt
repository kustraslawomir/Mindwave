package skustra.focusflow.ui.composables.session

import android.content.Context

sealed class SessionUiEvent {
    data class Start(val context: Context) : SessionUiEvent()
    object Pause : SessionUiEvent()
    object Resume : SessionUiEvent()
    object Stop : SessionUiEvent()
    data class SkipBreaks(val skip : Boolean) : SessionUiEvent()
}
