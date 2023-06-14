package skustra.focusflow.ui.composables.session

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import skustra.focusflow.data.database.dao.SessionCategoryDao
import skustra.focusflow.data.database.entity.SessionCategoryEntity
import skustra.focusflow.domain.usecase.sessionstate.SessionStateHandler
import javax.inject.Inject

@HiltViewModel
class SessionCategoryViewModel @Inject constructor(
    private val sessionCategoryDao: SessionCategoryDao
) : ViewModel() {

    fun getCategoryEntity(id: Int): SessionCategoryEntity {
        return sessionCategoryDao.getSessionCategoryById(id)
    }
}