package skustra.focusflow.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import skustra.focusflow.data.database.Table.SESSION_ARCHIVE
import skustra.focusflow.data.model.exceptions.toMs
import skustra.focusflow.data.model.session.Session

@Entity(tableName = SESSION_ARCHIVE)
class SessionArchiveEntity(
    @PrimaryKey val sessionId: String,
    @ColumnInfo(name = "date") val date: Long,
    @ColumnInfo(name = "duration_ms") val durationMs: Long
) {
    companion object {
        fun create(session: Session): SessionArchiveEntity {
            return SessionArchiveEntity(
                sessionId = session.sessionId,
                date = System.currentTimeMillis(),
                durationMs = session.duration.toMs()
            )
        }
    }
}

