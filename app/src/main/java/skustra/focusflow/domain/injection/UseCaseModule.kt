package skustra.focusflow.domain.injection

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import skustra.focusflow.domain.usecase.session.FocusSession
import skustra.focusflow.domain.usecase.session.FocusSessionUseCase

@Module
@InstallIn(ViewModelComponent::class)
object ActivityComponent {

    @Provides
    @ViewModelScoped
    fun provideFocusSessionUseCase() : FocusSession = FocusSessionUseCase()
}