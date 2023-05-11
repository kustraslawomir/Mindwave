package skustra.focusflow.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import skustra.focusflow.data.database.Table.SESSION_ARCHIVE
import skustra.focusflow.data.model.exceptions.toMs
import skustra.focusflow.data.model.session.Session
import skustra.focusflow.domain.utilities.dates.StatisticDateUtils
import java.util.Date

@Entity(tableName = SESSION_ARCHIVE)
class SessionArchiveEntity(
    @PrimaryKey val sessionId: String,
    @ColumnInfo(name = "date_ms") val dateMs: Long,
    @ColumnInfo(name = "formatted_date") val formattedDate: String,
    @ColumnInfo(name = "minutes") val minutes: Int
) {
    companion object {
        fun create(session: Session): SessionArchiveEntity {
            val dateMs = System.currentTimeMillis()
            return SessionArchiveEntity(
                sessionId = session.sessionId,
                minutes = session.sessionDuration(),
                formattedDate = StatisticDateUtils.format(Date(dateMs)),
                dateMs = dateMs
            )
        }
    }
}