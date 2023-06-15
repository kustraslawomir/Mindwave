package skustra.focusflow.ui.composables.session.panel

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import skustra.focusflow.data.model.timer.TimerState
import skustra.focusflow.ui.composables.session.SessionViewModel
import skustra.focusflow.ui.composables.session.panel.button.PanelState

@Composable
fun SessionPanelComposable(viewModel: SessionViewModel = viewModel()) {
    val sessionState by viewModel.getSessionFlow().collectAsStateWithLifecycle()
    when (sessionState.currentTimerState) {
        TimerState.Idle -> PanelState.Idle(viewModel)
        is TimerState.InProgress -> PanelState.Pause(viewModel)
        is TimerState.Paused -> PanelState.Resume(viewModel)
        is TimerState.Completed -> {
            //ignore
        }
    }
}