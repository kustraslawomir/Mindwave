package skustra.focusflow.ui.composables.session

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import skustra.focusflow.data.session.SessionState
import skustra.focusflow.ui.composables.session.arc.SessionFocusArc
import skustra.focusflow.ui.composables.session.panel.SessionPanelComposable

@Composable
fun SessionComposable(viewModel: SessionViewModel = viewModel()) {

    val sessionState by viewModel
        .sessionStateFlow
        .collectAsStateWithLifecycle(initialValue = SessionState.draft())

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        SessionFocusArc(
            sessionState = sessionState
        )
        SessionPanelComposable()
    }
}
