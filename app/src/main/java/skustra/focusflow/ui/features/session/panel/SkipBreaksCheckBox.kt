package skustra.focusflow.ui.features.session.panel

import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import skustra.focusflow.ui.utilities.composable.noRippleClickable
import skustra.focusflow.ui.localization.LocalizationKey
import skustra.focusflow.ui.localization.LocalizationManager

@Composable
fun SkipBreaksComposable(shouldSkipBreaks: (Boolean) -> Unit, skipBreaks: Boolean) {
    val checkedState = remember { mutableStateOf(skipBreaks) }
    Row(
        modifier = Modifier.noRippleClickable {
            checkedState.value = !checkedState.value
            shouldSkipBreaks(checkedState.value)
        },

        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            checked = checkedState.value,
            onCheckedChange = { isChecked ->
                checkedState.value = isChecked
                shouldSkipBreaks(isChecked)
            },
        )
        Text(
            text = LocalizationManager.getText(LocalizationKey.SkipBreaks),
            style = MaterialTheme.typography.bodySmall
        )
    }
}