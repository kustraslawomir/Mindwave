package skustra.focusflow.ui.composables.session.arc

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import skustra.focusflow.data.database.entity.SessionCategoryEntity
import skustra.focusflow.data.model.session.Session
import skustra.focusflow.data.model.timer.TimerState
import skustra.focusflow.ui.composables.session.SessionCategoryViewModel
import skustra.focusflow.ui.localization.LocalizationKey
import skustra.focusflow.ui.localization.LocalizationManager

@Composable
fun Category(
    session: Session,
    sessionCategoryViewModel: SessionCategoryViewModel = viewModel()
) {
    if (session.currentTimerState != TimerState.Idle) {
        return
    }

    val categoryText = if (session.category.id == SessionCategoryEntity.UnknownId) {
        LocalizationManager.getText(LocalizationKey.SetSessionCategory)
    } else {
        sessionCategoryViewModel.getCategoryEntity(session.category.id).name
    }

    Text(
        text = categoryText, style = MaterialTheme.typography.bodyMedium
    )
}