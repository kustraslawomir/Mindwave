package skustra.focusflow.data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import skustra.focusflow.data.database.Table
import skustra.focusflow.data.database.entity.SessionCategoryEntity

@Dao
interface SessionCategoryDao {

    @Query("SELECT * FROM ${Table.SESSION_CATEGORY}")
    fun getAll(): List<SessionCategoryEntity>

    @Query("SELECT * FROM ${Table.SESSION_CATEGORY} WHERE id =:id")
    fun getSessionCategoryById(id: Int): SessionCategoryEntity

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(entity: SessionCategoryEntity)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(entities: List<SessionCategoryEntity>)

    @Update
    fun update(entity: SessionCategoryEntity)

    @Delete
    fun delete(entity: SessionCategoryEntity)

    @Query("DELETE FROM ${Table.SESSION_CATEGORY}")
    fun deleteAll()
}