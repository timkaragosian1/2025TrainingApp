package com.timkaragosian.proflowapp.domain.usecase.flowresult

import com.timkaragosian.proflowapp.data.network.TodoDto
import com.timkaragosian.proflowapp.data.repository.HistoryRepositoryImpl
import com.timkaragosian.proflowapp.data.repository.TodoRepositoryImpl
import io.ktor.client.statement.HttpResponse

class CompleteTodoTaskUseCase(
    private val repo: TodoRepositoryImpl
) {
    suspend operator fun invoke(id:String): HttpResponse = repo.completeItem(id)
}