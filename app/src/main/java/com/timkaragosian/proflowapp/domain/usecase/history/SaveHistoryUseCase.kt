package com.timkaragosian.proflowapp.domain.usecase.history

import com.timkaragosian.proflowapp.data.repository.HistoryRepositoryImpl

class SaveHistoryUseCase(private val repo: HistoryRepositoryImpl) {
    suspend operator fun invoke(text:String) = repo.save(text, System.currentTimeMillis())
}