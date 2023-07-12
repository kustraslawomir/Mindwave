package skustra.focusflow.main

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import skustra.focusflow.domain.usecase.resources.DrawableProvider
import javax.inject.Inject


@HiltViewModel
class HomePageViewModel @Inject constructor(val drawableProvider: DrawableProvider) : ViewModel()