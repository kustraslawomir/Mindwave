package skustra.focusflow.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import skustra.focusflow.data.database.Table.SESSION_ARCHIVE
import skustra.focusflow.data.database.entity.SessionArchiveEntity

@Dao
interface SessionArchiveDao {

    @Query("SELECT * FROM $SESSION_ARCHIVE")
    fun getAll(): List<SessionArchiveEntity>

    @Query("SELECT * FROM $SESSION_ARCHIVE")
    fun getAllAsFlow(): Flow<List<SessionArchiveEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(archiveEntity: SessionArchiveEntity)
}