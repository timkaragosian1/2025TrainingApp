package com.timkaragosian.proflowapp.presentation.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.timkaragosian.proflowapp.R
import com.timkaragosian.proflowapp.data.network.TodoDto
import org.koin.androidx.compose.koinViewModel
import java.util.Date

@Composable
fun HomeScreen(
    vm: HomeViewModel = koinViewModel(),
    onTaskResults: (String, String, Boolean, Long) -> Unit,
    onNavigateToHistory: () -> Unit,
) {
    val todoList by vm.todoList.collectAsState()
    var showAddTodoDialog = remember { mutableStateOf(false) }
    var newTodoText = remember { mutableStateOf("") }

    vm.loadTodoList()

    when (showAddTodoDialog.value){
        true -> {
            AlertDialog(
                onDismissRequest = { showAddTodoDialog.value = false },
                title = { Text("New Todo") },
                text = {
                    OutlinedTextField(
                        value = newTodoText.value,
                        onValueChange = { newTodoText.value = it },
                        label = { Text("Task") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("todo_input")
                    )
                },
                confirmButton = {
                    Button(
                        onClick = {
                            vm.addTodoItem(
                                TodoDto(
                                    id = "${System.currentTimeMillis()}${newTodoText.value}",
                                    todo = newTodoText.value,
                                    completed = false,
                                    timestamp = System.currentTimeMillis()
                                )
                            )
                            showAddTodoDialog.value = false
                            vm.insertHistoryOnAction("Inserted Todo Task: ${ newTodoText.value } at timestamp ${System.currentTimeMillis()}")
                            vm.loadTodoList()
                        },
                        enabled = newTodoText.value.isNotBlank()
                    ) {
                        Text("Add")
                    }
                },
                dismissButton = {
                    OutlinedButton(onClick = { showAddTodoDialog.value = false }) {
                        Text("Cancel")
                    }
                }
            )
        }
        false -> {}
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        contentWindowInsets = WindowInsets.systemBars,
        topBar = {},
        floatingActionButton = {
            FloatingActionButton(
                onClick = { showAddTodoDialog.value = true },
                modifier = Modifier.testTag("fab_add_todo")
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add Todo")
            }
        },
        bottomBar = {
            BottomAppBar {
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Button(
                        onClick = { onNavigateToHistory() },
                        modifier = Modifier
                            .testTag("history_button")
                            .padding(start = 2.dp, end = 2.dp)
                    ) {
                        Text("History")
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
                onClick = { vm.loadTodoList() },
                modifier = Modifier.testTag("load_button")
            ) {
                Text(text = stringResource(id = R.string.home_button))
            }

            todoList.forEachIndexed { index, todoItem ->
                Spacer(Modifier.height(24.dp))
                Text(
                    text = "${stringResource(id = R.string.fetched)} ${todoItem.todo} (${if (todoItem.completed) "done" else "pending"})",
                    modifier = Modifier.testTag("todo_text_$index")
                )
                Button(
                    onClick = { onTaskResults(
                        todoItem.id,
                        todoItem.todo,
                        todoItem.completed,
                        todoItem.timestamp ) },
                    modifier = Modifier.testTag("submit_button_$index")
                ) {
                    Text(text = stringResource(id = R.string.goto_result_screen))
                }
            }
        }
    }
}