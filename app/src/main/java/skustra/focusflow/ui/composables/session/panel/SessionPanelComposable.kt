package skustra.focusflow.ui.composables.session.panel

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import skustra.focusflow.data.model.timer.TimerState
import skustra.focusflow.ui.composables.session.SessionViewModel
import skustra.focusflow.ui.localization.LocalizationKey
import skustra.focusflow.ui.localization.LocalizationManager
import skustra.focusflow.ui.theme.ButtonColor

@Composable
fun SessionPanelComposable(viewModel: SessionViewModel = viewModel()) {

    val sessionState by viewModel
        .getSessionStateFlow()
        .collectAsStateWithLifecycle()


    when (sessionState.currentTimerState) {
        TimerState.Completed, TimerState.Idle -> {

            Column(
                horizontalAlignment = Alignment.CenterHorizontally

            ) {
                if (viewModel.sessionIncludesBreaks()) {
                    SkipBreaksComposable()
                }
                StartSessionComposable()
            }
        }

        is TimerState.InProgress -> PauseSessionComposable()
        is TimerState.Paused -> ResumeSessionGroupComposable()
    }
}

@Composable
private fun StartSessionComposable(viewModel: SessionViewModel = viewModel()) {
    val context = LocalContext.current
    Box(
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .height(48.dp)
            .clip(shape = RoundedCornerShape(size = 12.dp))
            .background(color = ButtonColor)
            .clickable { viewModel.startSession(context) }
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = { viewModel.startSession(context) }) {
                Icon(
                    painter = painterResource(id = viewModel.resourceManager.getPlayIcon()),
                    contentDescription = "todo",
                    tint = Color.Black
                )
            }
            Text(
                text = LocalizationManager.getText(LocalizationKey.CreateSession),
                modifier = Modifier.padding(end = 16.dp, bottom = 2.dp),
                color = Color.Black
            )
        }
    }
}

@Composable
private fun PauseSessionComposable(viewModel: SessionViewModel = viewModel()) {
    CircleButton(
        onClick = {
            viewModel.pauseSession()
        },
        icon = viewModel.resourceManager.getPauseIcon()
    )
}

@Composable
private fun ResumeSessionGroupComposable() {
    Row(
        Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        StopSessionComposable()
        Spacer(modifier = Modifier.width(16.dp))
        ResumeSessionComposable()
    }
}

@Composable
private fun ResumeSessionComposable(viewModel: SessionViewModel = viewModel()) {
    CircleButton(
        onClick = {
            viewModel.resumeSession()
        },
        icon = viewModel.resourceManager.getResumeIcon()
    )
}

@Composable
private fun StopSessionComposable(viewModel: SessionViewModel = viewModel()) {
    CircleButton(
        onClick = {
            viewModel.stopSession()
        },
        icon = viewModel.resourceManager.getStopIcon(),
        color = Color.White
    )
}


