package com.timkaragosian.proflowapp.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.timkaragosian.proflowapp.data.network.TodoDto
import com.timkaragosian.proflowapp.domain.model.HistoryEntry
import com.timkaragosian.proflowapp.domain.usecase.home.GetTodoUseCase
import com.timkaragosian.proflowapp.domain.usecase.history.ObserveHistoryUseCase
import com.timkaragosian.proflowapp.domain.usecase.history.SaveHistoryUseCase
import com.timkaragosian.proflowapp.domain.usecase.home.AddItemUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch


class HomeViewModel(
    private val getTodo: GetTodoUseCase,
    private val addItem: AddItemUseCase,
    private val saveHistory: SaveHistoryUseCase,
    private val observeHistory: ObserveHistoryUseCase
) : ViewModel() {

    private val _todoList = MutableStateFlow<List<TodoDto>>(emptyList())
    val todoList: StateFlow<List<TodoDto>> = _todoList

    val history: StateFlow<List<HistoryEntry>> = observeHistory()
        .stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())

    fun loadSample() = viewModelScope.launch {
        getTodo().collect { _todoList.value = it.filterNotNull() }
    }

    fun addTodoItem(todoDto: TodoDto) = viewModelScope.launch { addItem(todoDto) }
}