package skustra.focusflow.ui.composables.session.panel

import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import skustra.focusflow.ui.composables.session.SessionViewModel
import skustra.focusflow.ui.extensions.noRippleClickable
import skustra.focusflow.ui.localization.LocalizationKey
import skustra.focusflow.ui.localization.LocalizationManager

@Preview
@Composable
fun SkipBreaksComposable(viewModel: SessionViewModel = viewModel()) {
    val checkedState = remember { mutableStateOf(false) }
    Row(
        modifier = Modifier.noRippleClickable{
            checkedState.value = !checkedState.value
            viewModel.skipBreaks(checkedState.value)
        },

        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            checked = checkedState.value,
            onCheckedChange = { isChecked ->
                checkedState.value = isChecked
                viewModel.skipBreaks(isChecked)
            },
        )
        Text(
            text = LocalizationManager.getText(LocalizationKey.SkipBreaks),
        )
    }
}