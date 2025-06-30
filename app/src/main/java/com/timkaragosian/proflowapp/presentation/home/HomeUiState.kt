package com.timkaragosian.proflowapp.presentation.home

import com.timkaragosian.proflowapp.data.network.TodoDto

data class HomeUiState(
    val todoList: List<TodoDto> = emptyList(),
    val showAddDialog: Boolean = false,
    val newTodoText: String = ""
)