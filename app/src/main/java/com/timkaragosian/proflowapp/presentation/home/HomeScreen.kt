package com.timkaragosian.proflowapp.presentation.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.timkaragosian.proflowapp.R

@Composable
fun HomeScreen(
    state: HomeUiState,
    onEvent: (HomeUiEvent) -> Unit,
) {
    LaunchedEffect(Unit) {
        onEvent(HomeUiEvent.LoadTodoList)
    }

    if (state.showAddDialog) {
        AlertDialog(
            onDismissRequest = { onEvent(HomeUiEvent.DismissDialog) },
            title = { Text("New Todo") },
            text = {
                OutlinedTextField(
                    value = state.newTodoText,
                    onValueChange = { onEvent(HomeUiEvent.TodoTextChanged(it)) },
                    label = { Text("Task") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("todo_input")
                )
            },
            confirmButton = {
                Button(
                    onClick = {
                        onEvent(HomeUiEvent.ConfirmAddTodo(state.newTodoText))
                    },
                    enabled = state.newTodoText.isNotBlank()
                ) {
                    Text("Add")
                }
            },
            dismissButton = {
                OutlinedButton(onClick = { onEvent(HomeUiEvent.DismissDialog) }) {
                    Text("Cancel")
                }
            }
        )
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        contentWindowInsets = WindowInsets.systemBars,
        floatingActionButton = {
            FloatingActionButton(
                onClick = { onEvent(HomeUiEvent.AddTodoClicked) },
                modifier = Modifier.testTag("fab_add_todo")
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add Todo")
            }
        },
        bottomBar = {
            BottomAppBar {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Button(
                        onClick = { onEvent(HomeUiEvent.NavigateToHistory) },
                        modifier = Modifier.testTag("history_button")
                    ) {
                        Text("History")
                    }
                    Button(
                        onClick = { onEvent(HomeUiEvent.Logout) },
                        modifier = Modifier.testTag("logout_button")
                    ) {
                        Text("Logout")
                    }
                }
            }
        },
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = stringResource(id = R.string.home_title),
                modifier = Modifier.testTag("home_title")
            )

            Spacer(Modifier.height(12.dp))

            Button(
                onClick = { onEvent(HomeUiEvent.LoadTodoList) },
                modifier = Modifier.testTag("load_button")
            ) {
                Text(text = stringResource(id = R.string.home_button))
            }

            state.todoList.forEachIndexed { index, todoItem ->
                Spacer(Modifier.height(24.dp))
                Text(
                    text = "${stringResource(id = R.string.fetched)} ${todoItem.todo} (${if (todoItem.completed) "done" else "pending"})",
                    modifier = Modifier.testTag("todo_text_$index")
                )
                Button(
                    onClick = {
                        onEvent(HomeUiEvent.NavigateToDetails(todoItem))
                    },
                    modifier = Modifier.testTag("submit_button_$index")
                ) {
                    Text(text = stringResource(id = R.string.goto_result_screen))
                }
            }
        }
    }
}
