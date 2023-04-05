package skustra.focusflow.domain.injection

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import skustra.focusflow.domain.usecase.session.FocusSession
import skustra.focusflow.domain.usecase.session.FocusSessionUseCase


@Module
@InstallIn(ViewModelComponent::class)
object ActivityComponent {

    @Provides
    fun provideFocusSessionUseCase() : FocusSession = FocusSessionUseCase()
}