package skustra.focusflow.data.database

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

object DatabaseMigrations {

    val MIGRATION_X_Y = object : Migration(0, 0) {
        override fun migrate(database: SupportSQLiteDatabase) {
            //database.execSQL("ALTER TABLE $SESSION_ARCHIVE ADD COLUMN category_id INTEGER NOT NULL DEFAULT ${SessionCategoryEntity.UnknownId}")
        }
    }
}