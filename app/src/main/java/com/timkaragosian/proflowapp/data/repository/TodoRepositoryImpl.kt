package com.timkaragosian.proflowapp.data.repository

import com.timkaragosian.proflowapp.data.network.TodoApi
import com.timkaragosian.proflowapp.data.network.TodoDto
import io.ktor.client.statement.HttpResponse

class TodoRepositoryImpl(
    private val api:TodoApi
) {
    suspend fun fetchTodoList(): List<TodoDto> = api.getTodoList()
    suspend fun addItem(todoItem: TodoDto):HttpResponse = api.postItem(todoItem)
}