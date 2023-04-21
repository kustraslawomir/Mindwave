package skustra.focusflow.domain.injection

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import skustra.focusflow.domain.usecase.resources.DrawableProviderImpl
import skustra.focusflow.domain.usecase.resources.DrawableProvider
import skustra.focusflow.domain.usecase.timer.Timer
import skustra.focusflow.domain.usecase.timer.TimerImpl

@Module
@InstallIn(ViewModelComponent::class)
object ActivityComponent {

    @Provides
    @ViewModelScoped
    fun provideTimerUseCase(): Timer = TimerImpl()

    @Provides
    @ViewModelScoped
    fun provideResourceManager(): DrawableProvider = DrawableProviderImpl()
}