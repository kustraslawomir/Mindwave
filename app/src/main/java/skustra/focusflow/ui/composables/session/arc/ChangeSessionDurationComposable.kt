package skustra.focusflow.ui.composables.session.arc

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.unit.dp
import skustra.focusflow.data.database.Constants
import skustra.focusflow.data.model.session.Session
import skustra.focusflow.data.model.timer.TimerState
import skustra.focusflow.ui.extensions.noRippleClickable
import skustra.focusflow.ui.theme.SessionDurationChangeButtonStyle

@Composable
fun ChangeSessionDurationComposable(
    session: Session,
    decreaseSessionDuration: () -> Unit,
    increaseSessionDuration: () -> Unit,
    isAvailableToDecrease: Boolean,
    isAvailableToIncrease: Boolean
) {
    if (session.currentTimerState != TimerState.Idle) {
        return
    }

    Row {
        Text(
            text = Constants.DECREASE_TEXT_SYMBOL,
            style = SessionDurationChangeButtonStyle,
            modifier = Modifier
                .noRippleClickable {
                    decreaseSessionDuration()
                }
                .alpha(if (isAvailableToDecrease) 1f else 0.2f))
        Spacer(Modifier.width(24.dp))
        Text(
            text = Constants.INCREASE_TEXT_SYMBOL,
            style = SessionDurationChangeButtonStyle,
            modifier = Modifier
                .noRippleClickable {
                    increaseSessionDuration()
                }
                .alpha(if (isAvailableToIncrease) 1f else 0.2f)
        )
    }
}