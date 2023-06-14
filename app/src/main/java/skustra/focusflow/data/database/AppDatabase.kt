package skustra.focusflow.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import skustra.focusflow.data.database.dao.SessionArchiveDao
import skustra.focusflow.data.database.dao.SessionCategoryDao
import skustra.focusflow.data.database.entity.SessionArchiveEntity
import skustra.focusflow.data.database.entity.SessionCategoryEntity

@Database(
    entities = [SessionArchiveEntity::class, SessionCategoryEntity::class],
    version = 8,
    autoMigrations = [],
    exportSchema = true
)
@TypeConverters(DateConverter::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun sessionArchiveDao(): SessionArchiveDao

    abstract fun sessionCategoryDao(): SessionCategoryDao
}