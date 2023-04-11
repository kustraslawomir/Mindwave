package skustra.focusflow.ui.composables.session.container

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import skustra.focusflow.domain.usecase.session.SessionConfig
import skustra.focusflow.ui.composables.session.FocusSessionViewModel
import skustra.focusflow.ui.composables.session.arc.SessionFocusArc
import skustra.focusflow.ui.localization.LocalizationKey
import skustra.focusflow.ui.localization.LocalizationManager

@Composable
fun SessionComposable(viewModel: FocusSessionViewModel = viewModel()) {
    val sessionState by viewModel.sessionStateFlow().collectAsState()
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        SessionFocusArc(sessionState = sessionState)
        ButtonProgressbar {
            viewModel.startSession(SessionConfig.defaultSessionDuration())
        }
    }
}

@Composable
private fun ButtonProgressbar(
    onClickButton: () -> Unit
) {
    val secondaryColor = MaterialTheme.colorScheme.secondary
    Button(
        onClick = {
            onClickButton()
        }, colors = ButtonDefaults.buttonColors(
            containerColor = secondaryColor
        )
    ) {
        Text(
            text = LocalizationManager.getText(LocalizationKey.Start)
        )
    }
}