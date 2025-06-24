package com.timkaragosian.proflowapp.presentation.flowresult

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.timkaragosian.proflowapp.domain.usecase.flowresult.CompleteTodoTaskUseCase
import com.timkaragosian.proflowapp.domain.usecase.flowresult.DeleteTodoTaskUseCase
import kotlinx.coroutines.launch

class FlowResultViewModel(
    private val deleteTodoTaskUseCase:DeleteTodoTaskUseCase,
    private val completeTodoTaskUseCase: CompleteTodoTaskUseCase,
):ViewModel() {
    fun deleteTodo(id:String) = viewModelScope.launch {
        deleteTodoTaskUseCase(id)
    }
    fun completeTodo(id:String) = viewModelScope.launch {
        completeTodoTaskUseCase(id)
    }
}