package com.timkaragosian.proflowapp.domain.usecase.flowresult

import com.timkaragosian.proflowapp.data.repository.TodoRepositoryImpl

class CompleteTodoTaskUseCase(
    private val repo: TodoRepositoryImpl
) {
    suspend operator fun invoke(id:String) = repo.completeItem(id)
}