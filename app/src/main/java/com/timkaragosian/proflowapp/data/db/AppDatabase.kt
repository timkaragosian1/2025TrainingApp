package com.timkaragosian.proflowapp.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.timkaragosian.proflowapp.domain.model.HistoryEntry

@Database(
    entities = [
        HistoryEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase: RoomDatabase() {
    abstract fun historyDao(): HistoryDao
}