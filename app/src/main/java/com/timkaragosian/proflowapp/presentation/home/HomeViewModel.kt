package com.timkaragosian.proflowapp.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.timkaragosian.proflowapp.data.network.TodoDto
import com.timkaragosian.proflowapp.domain.usecase.home.GetTodoUseCase
import com.timkaragosian.proflowapp.domain.usecase.history.SaveHistoryUseCase
import com.timkaragosian.proflowapp.domain.usecase.home.AddItemUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


class HomeViewModel(
    private val getTodo: GetTodoUseCase,
    private val addItem: AddItemUseCase,
    private val saveHistory: SaveHistoryUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    private val _event = MutableStateFlow<HomeUiEvent?>(null)
    val event: StateFlow<HomeUiEvent?> = _event.asStateFlow()

    fun onEvent(event: HomeUiEvent) {
        when (event) {
            is HomeUiEvent.AddTodoClicked -> _uiState.update { it.copy(showAddDialog = true) }
            is HomeUiEvent.DismissDialog -> _uiState.update { it.copy(showAddDialog = false) }
            is HomeUiEvent.TodoTextChanged -> _uiState.update { it.copy(newTodoText = event.text) }
            is HomeUiEvent.ConfirmAddTodo -> handleConfirmAdd()
            is HomeUiEvent.LoadTodoList -> loadTodoList()
            is HomeUiEvent.NavigateToDetails -> _event.value = event
            is HomeUiEvent.NavigateToHistory -> _event.value = event
            is HomeUiEvent.Logout -> _event.value = event
        }
    }

    private fun handleConfirmAdd() {
        val text = _uiState.value.newTodoText.trim()
        if (text.isBlank()) return

        val timestamp = System.currentTimeMillis()
        val todo = TodoDto(
            id = "$timestamp$text",
            todo = text,
            completed = false,
            timestamp = timestamp
        )

        viewModelScope.launch {
            addItem(todo)
            saveHistory("Inserted Todo Task: $text at timestamp $timestamp")
            loadTodoList()
        }

        _uiState.update { it.copy(newTodoText = "", showAddDialog = false) }
    }

    private fun loadTodoList() {
        viewModelScope.launch {
            getTodo().collect { list ->
                _uiState.update { it.copy(todoList = list.filterNotNull()) }
            }
        }
    }
}
