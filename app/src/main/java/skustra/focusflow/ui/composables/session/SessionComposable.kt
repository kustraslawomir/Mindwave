package skustra.focusflow.ui.composables.session

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import skustra.focusflow.ui.composables.session.arc.SessionFocusArc
import skustra.focusflow.ui.composables.session.panel.SessionPanelComposable

@Composable
fun SessionComposable(viewModel: SessionViewModel = viewModel()) {

    val sessionState by viewModel
        .sessionStateFlow
        .collectAsStateWithLifecycle()

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        SessionFocusArc(sessionState = sessionState)
        SessionPanelComposable()
    }
}
