package skustra.focusflow.ui.features.permission

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import skustra.focusflow.domain.usecase.resources.DrawableProvider
import skustra.focusflow.ui.localization.LocalizationKey
import skustra.focusflow.ui.localization.LocalizationManager
import skustra.focusflow.ui.utilities.composable.noRippleClickable

@Composable
fun NotificationPermissionBottomSheet(
    drawableProvider: DrawableProvider,
    onAllowed: () -> Unit
) {
    Column(
        modifier = Modifier.padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = LocalizationManager.getText(LocalizationKey.PostPermissionNeedMessageRationale)
        )
        Spacer(modifier = Modifier.height(32.dp))
        Text(
            text = LocalizationManager.getText(LocalizationKey.Allow),
            color = MaterialTheme.colorScheme.primary,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier
                .noRippleClickable {
                    onAllowed()
                }
                .align(alignment = Alignment.End)
                .padding(end = 16.dp)

        )
        Spacer(modifier = Modifier.height(64.dp))
    }
}

