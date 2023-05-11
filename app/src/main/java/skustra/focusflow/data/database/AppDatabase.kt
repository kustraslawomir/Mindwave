package skustra.focusflow.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import skustra.focusflow.data.database.dao.SessionArchiveDao
import skustra.focusflow.data.database.entity.SessionArchiveEntity

@Database(entities = [SessionArchiveEntity::class], version = 5)
@TypeConverters(DateConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun sessionArchiveDao(): SessionArchiveDao
}