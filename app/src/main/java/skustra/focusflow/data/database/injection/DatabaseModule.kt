package skustra.focusflow.data.database.injection

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import skustra.focusflow.data.database.AppDatabase
import skustra.focusflow.data.database.Constants.DATABASE
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provide(@ApplicationContext context: Context) = Room.databaseBuilder(
        context, AppDatabase::class.java, DATABASE
    )
        .fallbackToDestructiveMigration()
        .build()

    @Provides
    @Singleton
    fun provideSessionArchiveDao(database: AppDatabase) = database.sessionArchiveDao()

    @Provides
    @Singleton
    fun provideSessionCategoryDao(database: AppDatabase) = database.sessionCategoryDao()
}