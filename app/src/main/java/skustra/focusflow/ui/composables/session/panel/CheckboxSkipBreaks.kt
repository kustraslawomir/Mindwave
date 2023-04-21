package skustra.focusflow.ui.composables.session.panel

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import skustra.focusflow.ui.localization.LocalizationKey
import skustra.focusflow.ui.localization.LocalizationManager

@Preview
@Composable
fun CheckboxSkipBreaks() {
    val checkedState = remember { mutableStateOf(false) }
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            checked = checkedState.value,
            onCheckedChange = { checkedState.value = it },
        )
        Text(
            text = LocalizationManager.getText(LocalizationKey.SkipBreaks),
        )
    }
}