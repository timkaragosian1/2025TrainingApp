package com.timkaragosian.proflowapp.domain.usecase

import com.timkaragosian.proflowapp.data.network.TodoDto
import com.timkaragosian.proflowapp.data.repository.TodoRepositoryImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*

class GetTodoUseCase(
    private val repo: TodoRepositoryImpl
) {
    operator fun invoke(): Flow<List<TodoDto>> = flow {
        emit(repo.fetchTodoList())
    }.flowOn(Dispatchers.IO)
}