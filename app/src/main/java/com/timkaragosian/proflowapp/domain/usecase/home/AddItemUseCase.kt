package com.timkaragosian.proflowapp.domain.usecase.home

import com.timkaragosian.proflowapp.data.network.TodoDto
import com.timkaragosian.proflowapp.data.repository.TodoRepositoryImpl
import io.ktor.client.statement.HttpResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class AddItemUseCase(
    private val repo: TodoRepositoryImpl
) {
    suspend operator fun invoke(todoDto: TodoDto): HttpResponse = repo.addItem(todoDto)
}