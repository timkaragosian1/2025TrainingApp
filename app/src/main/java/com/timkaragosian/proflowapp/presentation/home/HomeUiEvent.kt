package com.timkaragosian.proflowapp.presentation.home

import com.timkaragosian.proflowapp.data.network.TodoDto

sealed class HomeUiEvent {
    data object LoadTodoList : HomeUiEvent()
    data object AddTodoClicked : HomeUiEvent()
    data object DismissDialog : HomeUiEvent()
    data class TodoTextChanged(val text: String) : HomeUiEvent()
    data class ConfirmAddTodo(val text: String) : HomeUiEvent()
    data object NavigateToHistory : HomeUiEvent()
    data class NavigateToDetails(val todo: TodoDto) : HomeUiEvent()
    data object Logout : HomeUiEvent()
}