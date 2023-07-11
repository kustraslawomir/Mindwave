package skustra.focusflow.ui.composables.session.pagericons

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import skustra.focusflow.domain.usecase.resources.DrawableProvider
import javax.inject.Inject

@HiltViewModel
class PagerIconsViewModel @Inject constructor(val drawableProvider: DrawableProvider) :
    ViewModel()