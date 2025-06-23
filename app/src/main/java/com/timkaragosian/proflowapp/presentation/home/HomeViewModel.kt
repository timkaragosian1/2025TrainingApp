package com.timkaragosian.proflowapp.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.timkaragosian.proflowapp.data.db.HistoryEntity
import com.timkaragosian.proflowapp.data.network.TodoDto
import com.timkaragosian.proflowapp.domain.model.HistoryEntry
import com.timkaragosian.proflowapp.domain.usecase.GetTodoUseCase
import com.timkaragosian.proflowapp.domain.usecase.ObserveHistoryUseCase
import com.timkaragosian.proflowapp.domain.usecase.SaveHistoryUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch


class HomeViewModel(
    private val getTodo: GetTodoUseCase,
    private val saveHistory: SaveHistoryUseCase,
    private val observeHistory: ObserveHistoryUseCase
) : ViewModel() {
    private val _todoList = MutableStateFlow<List<TodoDto?>>(emptyList())
    val todoList:StateFlow<List<TodoDto?>> = _todoList

    private val _history = observeHistory().stateIn(
        viewModelScope,
        SharingStarted.Eagerly,
        emptyList()
    )
    val history: StateFlow<List<HistoryEntry>> = _history

    fun loadSample() = viewModelScope.launch {
        getTodo().collect { _todoList.value = it }
    }
}