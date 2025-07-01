package com.timkaragosian.proflowapp.presentation.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.timkaragosian.proflowapp.data.network.TodoDto
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeScreenContainer(
    vm: HomeViewModel = koinViewModel(),
    onNavigateToHistory: () -> Unit,
    onLogout: () -> Unit,
    onTaskResults: (TodoDto) -> Unit,
) {
    val todoList by vm.todoList.collectAsState()
    val showDialog by vm.showAddDialog.collectAsState()
    val newTodoText by vm.newTodoText.collectAsState()

    HomeScreen(
        state = HomeUiState(todoList, showDialog, newTodoText),
        getTodoList = { vm.loadTodoList() },
        insertHistoryOnAction = { vm.insertHistoryOnAction(it) },
        onNavigateToHistory = onNavigateToHistory,
        onNavigateToTaskDetails = { id, todo, completed, timestamp ->
            onTaskResults(TodoDto(id, todo, completed, timestamp))
        },
        onTodoTextChange = vm::onTodoTextChange,
        onAddTodoClicked = vm::onAddTodoClicked,
        onConfirmAddTodo = { vm.onConfirmAddTodo() },
        onDismissDialog = vm::onDismissDialog,
        onLogout = onLogout
    )
}
