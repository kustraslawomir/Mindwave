package skustra.focusflow.ui.composables.session.arc
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import skustra.focusflow.data.database.Constants
import skustra.focusflow.data.model.session.Session
import skustra.focusflow.data.model.timer.TimerState
import skustra.focusflow.ui.composables.session.SessionViewModel
import skustra.focusflow.ui.extensions.noRippleClickable
import skustra.focusflow.ui.theme.SessionDurationChangeButtonStyle

@Composable
fun ChangeSessionDurationComposable(
    session: Session, viewModel: SessionViewModel = viewModel()
) {
    if (session.currentTimerState != TimerState.Idle) {
        return
    }

    Row(
        modifier = Modifier.padding(top = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = Constants.DECREASE_TEXT_SYMBOL,
            style = SessionDurationChangeButtonStyle,
            modifier = Modifier
                .noRippleClickable {
                    viewModel.decreaseSessionDuration()
                }
                .alpha(if (viewModel.isAvailableToDecrease()) 1f else 0.2f))
        Spacer(Modifier.width(24.dp))
        Text(
            text = Constants.INCREASE_TEXT_SYMBOL,
            style = SessionDurationChangeButtonStyle,
            modifier = Modifier
                .noRippleClickable {
                    viewModel.increaseSessionDuration()
                }
                .alpha(if (viewModel.isAvailableToIncrease()) 1f else 0.2f)
        )
    }
}