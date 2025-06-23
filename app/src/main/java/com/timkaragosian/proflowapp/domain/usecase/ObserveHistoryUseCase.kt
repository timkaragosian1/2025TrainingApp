package com.timkaragosian.proflowapp.domain.usecase

import com.timkaragosian.proflowapp.data.repository.HistoryRepositoryImpl
import com.timkaragosian.proflowapp.domain.model.HistoryEntry
import kotlinx.coroutines.flow.Flow

class ObserveHistoryUseCase(private val repo:HistoryRepositoryImpl) {
    operator fun invoke(): Flow<List<HistoryEntry>> = repo.observeHistory()
}