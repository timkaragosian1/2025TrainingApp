package com.timkaragosian.proflowapp.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface HistoryDao {
    @Insert
    suspend fun insert(item:HistoryEntity)

    @Query("SELECT * FROM history ORDER BY time DESC")
    fun observeAll(): Flow<List<HistoryEntity>>
}