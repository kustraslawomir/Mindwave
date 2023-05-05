package skustra.focusflow.domain.injection

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import skustra.focusflow.domain.usecase.resources.DrawableProvider
import skustra.focusflow.domain.usecase.resources.DrawableProviderImpl
import skustra.focusflow.domain.usecase.timer.Timer
import skustra.focusflow.domain.usecase.timer.TimerImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object GlobalComponent {

    @Provides
    @Singleton
    fun provideTimerUseCase(): Timer = TimerImpl()

    @Provides
    @Singleton
    fun provideResourceManager(): DrawableProvider = DrawableProviderImpl()
}