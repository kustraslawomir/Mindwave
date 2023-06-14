package skustra.focusflow.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import skustra.focusflow.data.database.Table



@Entity(tableName = Table.SESSION_CATEGORY, indices = [Index(value = ["id","name"], unique = true)])
data class SessionCategoryEntity(
    @ColumnInfo(name = "name")
    var name: String
){
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int = 0

    companion object {
        const val UnknownId = -1
        val Default = SessionCategoryEntity(
            name = ""
        ).apply {
            id = UnknownId
        }
    }
}