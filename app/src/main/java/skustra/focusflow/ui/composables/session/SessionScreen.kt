package skustra.focusflow.ui.composables.session

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import skustra.focusflow.ui.composables.session.arc.SessionFocusArc
import skustra.focusflow.ui.composables.session.description.SessionDescriptionComposable
import skustra.focusflow.ui.composables.session.panel.SessionPanelComposable

@Composable
fun SessionScreen(viewModel: SessionViewModel = viewModel()) {

    val sessionState by viewModel
        .getSessionFlow()
        .collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 80.dp),
    ) {

        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxSize(),
            contentAlignment = Alignment.BottomCenter

        ) {
            SessionDescriptionComposable()
        }
        Box(
            modifier = Modifier
                .weight(2f)
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            SessionFocusArc(
                session = sessionState
            )
        }
        Box(
            modifier = Modifier
                .weight(0.6f)
                .fillMaxSize(),
            contentAlignment = Alignment.BottomCenter
        ) {
            SessionPanelComposable()
        }
    }
}
