package skustra.focusflow.domain.injection

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import skustra.focusflow.domain.usecase.resources.DrawableProvider
import skustra.focusflow.domain.usecase.resources.DrawableProviderImpl
import skustra.focusflow.domain.usecase.sessionstate.SessionStateEmitter
import skustra.focusflow.domain.usecase.sessionstate.SessionStateEmitterImpl
import skustra.focusflow.domain.usecase.sessionstate.SessionStateHandler
import skustra.focusflow.ui.notification.SessionServiceNotificationManager
import skustra.focusflow.ui.notification.SessionServiceNotificationManagerImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object GlobalComponent {

    @Provides
    fun provideSessionServiceNotificationManager(@ApplicationContext context: Context)
            : SessionServiceNotificationManager = SessionServiceNotificationManagerImpl(context)

    @Provides
    @Singleton
    fun provideResourceManager(): DrawableProvider = DrawableProviderImpl()

    @Provides
    @Singleton
    fun provideTimerStateHandler(@ApplicationContext context: Context) = SessionStateHandler(context)

    @Provides
    @Singleton
    fun provideTimerStateEmitter(): SessionStateEmitter = SessionStateEmitterImpl()
}