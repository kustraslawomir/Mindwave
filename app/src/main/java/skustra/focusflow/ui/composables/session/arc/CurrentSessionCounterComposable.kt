package skustra.focusflow.ui.composables.session.arc
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import skustra.focusflow.data.model.session.Session
import skustra.focusflow.data.model.session.SessionPartType
import skustra.focusflow.data.model.timer.TimerState
import skustra.focusflow.ui.localization.LocalizationKey
import skustra.focusflow.ui.localization.LocalizationManager

@Composable
fun CurrentSessionCounter(sessionState: Session) {
    if (sessionState.currentTimerState !is TimerState.InProgress) {
        return
    }

    val sessionStateStatusText = when (sessionState.currentSessionPart().type) {
        SessionPartType.Work -> {
            val workParts = sessionState.parts.filter { sessionPart ->
                sessionPart.type == SessionPartType.Work
            }

            val currentWorkIndex = workParts.indexOfFirst { sessionPart ->
                sessionPart.id == sessionState.currentSessionPart().id
            } + 1

            "$currentWorkIndex/${workParts.size}"
        }
        SessionPartType.Break -> LocalizationManager.getText(LocalizationKey.Break)
    }
    Text(
        text = sessionStateStatusText, style = MaterialTheme.typography.bodyMedium
    )
}