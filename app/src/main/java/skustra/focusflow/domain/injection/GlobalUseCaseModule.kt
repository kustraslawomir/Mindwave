package skustra.focusflow.domain.injection

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import skustra.focusflow.data.database.dao.SessionArchiveDao
import skustra.focusflow.data.repositories.sessionarchive.SessionArchiveRepository
import skustra.focusflow.domain.ports.SessionArchiveRepositoryPort
import skustra.focusflow.domain.ports.StatisticsRepositoryPort
import skustra.focusflow.data.repositories.statistics.StatisticsRepository
import skustra.focusflow.domain.usecase.playsound.PlaySoundUseCase
import skustra.focusflow.domain.usecase.resources.DrawableProvider
import skustra.focusflow.domain.usecase.resources.DrawableProviderImpl
import skustra.focusflow.domain.usecase.sessionarchive.SessionArchiveUseCase
import skustra.focusflow.domain.usecase.sessionarchive.archive.GetSessionArchiveUseCase
import skustra.focusflow.domain.usecase.sessionarchive.archive.SetSessionArchiveUseCase
import skustra.focusflow.domain.usecase.sessionarchive.statistics.GetStatisticsUseCase
import skustra.focusflow.domain.usecase.sessionstate.SessionStateEmitter
import skustra.focusflow.domain.usecase.sessionstate.SessionStateEmitterImpl
import skustra.focusflow.domain.usecase.sessionstate.SessionStateHandler
import skustra.focusflow.domain.usecase.statenotification.BreakCompletedNotification
import skustra.focusflow.domain.usecase.statenotification.SessionCompletedNotification
import skustra.focusflow.domain.usecase.statenotification.SessionStartedNotification
import skustra.focusflow.domain.usecase.statenotification.WorkCompletedNotification
import skustra.focusflow.domain.usecase.vibrate.SingleVibrationUseCase
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
    fun provideStatisticsPort(dao: SessionArchiveDao): StatisticsRepositoryPort {
        return StatisticsRepository(dao)
    }

    @Provides
    @Singleton
    fun provideSessionArchiveRepositoryPort(dao: SessionArchiveDao): SessionArchiveRepositoryPort {
        return SessionArchiveRepository(dao)
    }

    @Provides
    @Singleton
    fun provideGetStatisticsUseCase(port: StatisticsRepositoryPort): GetStatisticsUseCase {
        return GetStatisticsUseCase(port)
    }

    @Provides
    @Singleton
    fun provideGetSessionArchiveUseCase(port: SessionArchiveRepositoryPort): GetSessionArchiveUseCase {
        return GetSessionArchiveUseCase(port)
    }

    @Provides
    @Singleton
    fun provideSetSessionArchiveUseCase(port: SessionArchiveRepositoryPort): SetSessionArchiveUseCase {
        return SetSessionArchiveUseCase(port)
    }

    @Provides
    @Singleton
    fun provideSessionArchiveDataUseCase(
        getStatisticsUseCase: GetStatisticsUseCase,
        getSessionArchiveUseCase: GetSessionArchiveUseCase,
        setSessionArchiveUseCase: SetSessionArchiveUseCase
    ): SessionArchiveUseCase {
        return SessionArchiveUseCase(
            getSessionArchiveUseCase,
            setSessionArchiveUseCase,
            getStatisticsUseCase
        )
    }

    @Provides
    @Singleton
    fun provideTimerStateHandler(
        @ApplicationContext context: Context,
        workCompletedNotification: WorkCompletedNotification,
        breakCompletedNotification: BreakCompletedNotification,
        sessionCompletedNotification: SessionCompletedNotification,
        sessionStartedNotification: SessionStartedNotification,
        sessionStateEmitter: SessionStateEmitter,
        sessionArchiveRepository: SessionArchiveRepository
    ) =
        SessionStateHandler(
            context,
            workCompletedNotification,
            breakCompletedNotification,
            sessionCompletedNotification,
            sessionStartedNotification,
            sessionStateEmitter,
            sessionArchiveRepository
        )

    @Provides
    @Singleton
    fun provideTimerStateEmitter(): SessionStateEmitter = SessionStateEmitterImpl()

    @Provides
    @Singleton
    fun providePlaySoundUseCase(@ApplicationContext context: Context) = PlaySoundUseCase(context)

    @Provides
    @Singleton
    fun provideSingleVibrationUseCase(@ApplicationContext context: Context) =
        SingleVibrationUseCase(context)

    @Provides
    @Singleton
    fun provideSessionArchiveRepository(sessionArchiveDao: SessionArchiveDao) =
        SessionArchiveRepository(sessionArchiveDao)
}