package com.timkaragosian.proflowapp.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.Instant

@Entity(tableName = "history")
data class HistoryEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val inputText: String,
    val time: Long = Instant.now().epochSecond
)
