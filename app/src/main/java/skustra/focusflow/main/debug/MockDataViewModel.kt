package skustra.focusflow.main.debug

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import skustra.focusflow.domain.usecase.mock.MockSessionArchiveData
import javax.inject.Inject

@HiltViewModel
class MockDataViewModel @Inject constructor(
    val mockSessionArchiveData: MockSessionArchiveData
) : ViewModel()