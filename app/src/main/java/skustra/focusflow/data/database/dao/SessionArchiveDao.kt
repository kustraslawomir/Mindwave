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

    @Query("SELECT * FROM $SESSION_ARCHIVE ORDER BY date_ms DESC")
    fun getAll(): List<SessionArchiveEntity>

    @Query("SELECT * FROM $SESSION_ARCHIVE ORDER BY date_ms DESC")
    fun getAllAsFlow(): Flow<List<SessionArchiveEntity>>

    @Query("SELECT * FROM $SESSION_ARCHIVE ORDER BY date_ms DESC LIMIT 1")
    fun getLastEntity() : SessionArchiveEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(archiveEntity: SessionArchiveEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(archiveEntities: List<SessionArchiveEntity>)

    @Query("DELETE FROM $SESSION_ARCHIVE")
    fun clearTable()

}