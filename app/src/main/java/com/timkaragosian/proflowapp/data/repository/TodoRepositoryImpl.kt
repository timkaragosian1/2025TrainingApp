package com.timkaragosian.proflowapp.data.repository

import com.timkaragosian.proflowapp.data.network.TodoApi
import com.timkaragosian.proflowapp.data.network.TodoDto

class TodoRepositoryImpl(
    private val api:TodoApi
) {
    suspend fun fetchTodoList(): List<TodoDto> = api.getTodoList()
}