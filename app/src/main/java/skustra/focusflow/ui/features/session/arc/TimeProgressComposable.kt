package skustra.focusflow.ui.features.session.arc

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import skustra.focusflow.data.model.session.Session
import skustra.focusflow.data.model.timer.TimerState
import skustra.focusflow.ui.localization.LocalizationKey
import skustra.focusflow.ui.localization.LocalizationManager
import skustra.focusflow.ui.theme.SessionDurationButtonStyle

@Composable
fun TimeProgress(
    session: Session
) {
    val minutesLeft = when (val timerState = session.currentTimerState) {
        is TimerState.InProgress -> timerState.progress.minutesLeft.toString()
        is TimerState.Paused -> timerState.progress.minutesLeft.toString()
        is TimerState.Idle -> session.sessionDuration().toString()
        else -> 0.toString()
    }

    Row(verticalAlignment = Alignment.Bottom) {
        Text(
            text = minutesLeft,
            style = SessionDurationButtonStyle
        )
        Text(
            text = LocalizationManager.getText(LocalizationKey.MinutesShort),
            modifier = Modifier
                .padding(horizontal = 8.dp, vertical = 6.dp),
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

