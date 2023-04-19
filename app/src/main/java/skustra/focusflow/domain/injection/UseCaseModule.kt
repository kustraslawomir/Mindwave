package skustra.focusflow.domain.injection

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import skustra.focusflow.domain.usecase.resources.DrawableProviderImpl
import skustra.focusflow.domain.usecase.resources.DrawableProvider
import skustra.focusflow.domain.usecase.session.SessionManager
import skustra.focusflow.domain.usecase.session.SessionManagerImpl
import javax.inject.Singleton

@Module
@InstallIn(ViewModelComponent::class)
object ActivityComponent {

    @Provides
    @ViewModelScoped
    fun provideFocusSessionUseCase(): SessionManager = SessionManagerImpl()

    @Provides
    @ViewModelScoped
    fun provideResourceManager(): DrawableProvider = DrawableProviderImpl()
}