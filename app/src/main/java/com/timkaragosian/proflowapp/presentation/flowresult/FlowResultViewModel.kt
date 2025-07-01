package com.timkaragosian.proflowapp.presentation.flowresult

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.timkaragosian.proflowapp.domain.usecase.flowresult.CompleteTodoTaskUseCase
import com.timkaragosian.proflowapp.domain.usecase.flowresult.DeleteTodoTaskUseCase
import com.timkaragosian.proflowapp.domain.usecase.history.SaveHistoryUseCase
import kotlinx.coroutines.launch

class FlowResultViewModel(
    private val deleteTodoTaskUseCase:DeleteTodoTaskUseCase,
    private val completeTodoTaskUseCase: CompleteTodoTaskUseCase,
    private val saveHistory: SaveHistoryUseCase
):ViewModel() {
    fun deleteTodo(id:String) = viewModelScope.launch {
        deleteTodoTaskUseCase(id)
    }

    fun completeTodo(id:String) = viewModelScope.launch {
        completeTodoTaskUseCase(id)
    }

    fun saveHistory(task:String) = viewModelScope.launch {
        saveHistory.invoke(task)
    }
}