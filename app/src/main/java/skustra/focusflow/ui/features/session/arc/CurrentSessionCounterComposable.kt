package skustra.focusflow.ui.features.session.arc
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import skustra.focusflow.data.model.session.Session
import skustra.focusflow.data.model.session.SessionPartType
import skustra.focusflow.data.model.timer.TimerState
import skustra.focusflow.ui.localization.LocalizationKey
import skustra.focusflow.ui.localization.LocalizationManager

@Composable
fun CurrentSessionCounter(session: Session) {
    if (session.currentTimerState !is TimerState.InProgress) {
        return
    }

    val sessionStateStatusText = when (session.currentSessionPart().type) {
        SessionPartType.Work -> {
            val workParts = session.parts.filter { sessionPart ->
                sessionPart.type == SessionPartType.Work
            }

            val currentWorkIndex = workParts.indexOfFirst { sessionPart ->
                sessionPart.id == session.currentSessionPart().id
            } + 1

            "$currentWorkIndex/${workParts.size}"
        }
        SessionPartType.Break -> LocalizationManager.getText(LocalizationKey.Break)
    }
    Text(
        text = sessionStateStatusText, style = MaterialTheme.typography.bodySmall
    )
}