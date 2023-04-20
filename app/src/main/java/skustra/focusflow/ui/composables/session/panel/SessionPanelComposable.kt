package skustra.focusflow.ui.composables.session.panel

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import skustra.focusflow.data.session.SessionState
import skustra.focusflow.data.timer.TimerState
import skustra.focusflow.domain.usecase.session.SessionConfig
import skustra.focusflow.ui.composables.session.SessionViewModel

@Composable
fun SessionPanelComposable(viewModel: SessionViewModel = viewModel()) {

    val sessionState by viewModel
        .sessionStateFlow
        .collectAsStateWithLifecycle(initialValue = SessionState.draft())

    when (sessionState.currentTimerState) {
        TimerState.Completed, TimerState.Idle -> CreateSessionButton()
        is TimerState.InProgress -> PauseSessionButton()
        is TimerState.Paused -> ResumeSession()
    }
}

@Composable
private fun CreateSessionButton(viewModel: SessionViewModel = viewModel()) {
    CircleButton(
        onClick = {
            viewModel.createSession(SessionConfig.defaultSessionDuration())
        },
        icon = viewModel.resourceManager.getPlayIcon()
    )
}

@Composable
private fun PauseSessionButton(viewModel: SessionViewModel = viewModel()) {
    CircleButton(
        onClick = {
            viewModel.pauseSession()
        },
        icon = viewModel.resourceManager.getPauseIcon()
    )
}

@Composable
private fun ResumeSessionButton(viewModel: SessionViewModel = viewModel()) {
    CircleButton(
        onClick = {
            viewModel.resumeSession()
        },
        icon = viewModel.resourceManager.getResumeIcon()
    )
}

@Composable
private fun ResumeSession() {
    Row(
        Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        StopSessionButton()
        Spacer(modifier = Modifier.width(16.dp))
        ResumeSessionButton()
    }
}

@Composable
private fun StopSessionButton(viewModel: SessionViewModel = viewModel()) {
    CircleButton(
        onClick = {
            viewModel.stopSession()
        },
        icon = viewModel.resourceManager.getStopIcon()
    )
}

@Composable
private fun CircleButton(onClick: () -> Unit, icon: Int) {
    IconButton(
        onClick = { onClick() },
        modifier = Modifier
            .size(46.dp)
            .background(Color.White, CircleShape)
            .padding(6.dp),
        content = {
            Icon(painter = painterResource(id = icon), "$icon", tint = Color.Black)
        }
    )
}
