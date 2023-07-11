package skustra.focusflow.ui.composables.session.panel

import androidx.compose.runtime.Composable
import skustra.focusflow.data.model.session.Session
import skustra.focusflow.data.model.timer.TimerState
import skustra.focusflow.domain.usecase.resources.DrawableProvider
import skustra.focusflow.ui.composables.session.panel.button.PanelState

@Composable
fun SessionPanelComposable(
    session: Session,
    startSession: () -> Unit,
    pauseSession: () -> Unit,
    resumeSession: () -> Unit,
    stopSession: () -> Unit,
    sessionIncludesBreaks: Boolean,
    skipBreaks: Boolean,
    drawableProvider: DrawableProvider,
    shouldSkipBreaks: (Boolean) -> Unit
) {
    when (session.currentTimerState) {
        TimerState.Idle -> PanelState.Idle(
            session = session,
            sessionIncludesBreaks = sessionIncludesBreaks,
            skipBreaks = skipBreaks,
            startSession = startSession,
            drawableProvider = drawableProvider,
            shouldSkipBreaks = shouldSkipBreaks
        )

        is TimerState.InProgress -> PanelState.Pause(
            drawableProvider = drawableProvider,
            pauseSession = pauseSession
        )

        is TimerState.Paused -> PanelState.Resume(
            drawableProvider = drawableProvider,
            resumeSession = resumeSession,
            stopSession = stopSession
        )

        is TimerState.Completed -> {
            //ignore
        }
    }
}