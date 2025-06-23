package com.timkaragosian.proflowapp.data.repository

import com.timkaragosian.proflowapp.data.db.HistoryDao
import com.timkaragosian.proflowapp.data.db.HistoryEntity
import com.timkaragosian.proflowapp.data.db.asDomain
import com.timkaragosian.proflowapp.domain.model.HistoryEntry
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class HistoryRepositoryImpl(private val dao: HistoryDao) {
    fun observeHistory(): Flow<List<HistoryEntry>> =
        dao.observeAll().map { list -> list.map { it.asDomain() } }

    suspend fun save(text: String){
        dao.insert(HistoryEntity(inputText = text))
    }
}