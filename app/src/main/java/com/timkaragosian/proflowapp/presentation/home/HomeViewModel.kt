package com.timkaragosian.proflowapp.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.timkaragosian.proflowapp.data.network.TodoDto
import com.timkaragosian.proflowapp.domain.usecase.home.GetTodoUseCase
import com.timkaragosian.proflowapp.domain.usecase.history.SaveHistoryUseCase
import com.timkaragosian.proflowapp.domain.usecase.home.AddItemUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


class HomeViewModel(
    private val getTodo: GetTodoUseCase,
    private val addItem: AddItemUseCase,
    private val saveHistory: SaveHistoryUseCase
) : ViewModel() {

    private val _todoList = MutableStateFlow<List<TodoDto>>(emptyList())
    val todoList: StateFlow<List<TodoDto>> = _todoList

    private val _showAddDialog = MutableStateFlow(false)
    val showAddDialog: StateFlow<Boolean> = _showAddDialog

    private val _newTodoText = MutableStateFlow("")
    val newTodoText: StateFlow<String> = _newTodoText

    fun loadTodoList() {
        viewModelScope.launch {
            getTodo().collect { list ->
                _todoList.value = list.filterNotNull()
            }
        }
    }

    fun onTodoTextChange(text: String) {
        _newTodoText.value = text
    }

    fun onAddTodoClicked() {
        _showAddDialog.value = true
    }

    fun onDismissDialog() {
        _showAddDialog.value = false
    }

    fun onConfirmAddTodo() {
        val todoText = _newTodoText.value.trim()
        if (todoText.isBlank()) return

        val timestamp = System.currentTimeMillis()
        val newTodo = TodoDto(
            id = "$timestamp$todoText",
            todo = todoText,
            completed = false,
            timestamp = timestamp
        )

        viewModelScope.launch {
            addItem(newTodo)
            saveHistory("Inserted Todo Task: $todoText at timestamp $timestamp")
            loadTodoList()
        }

        _newTodoText.value = ""
        _showAddDialog.value = false
    }

    fun insertHistoryOnAction(actionText: String) {
        viewModelScope.launch {
            saveHistory(actionText)
        }
    }
}