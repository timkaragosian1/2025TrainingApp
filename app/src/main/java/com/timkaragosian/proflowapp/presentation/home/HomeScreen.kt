package com.timkaragosian.proflowapp.presentation.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.timkaragosian.proflowapp.R
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeScreen(
    vm: HomeViewModel = koinViewModel(),
    onSubmit: (String) -> Unit
) {
    val todoList by vm.todoList.collectAsState()
    //val history by vm.history.collectAsState()
    
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        contentWindowInsets = WindowInsets.systemBars,
        topBar = {}
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = stringResource(id = R.string.home_message))
            Spacer(Modifier.height(12.dp))
            Button(onClick = { vm.loadSample() }) {
                Text(text = stringResource(id = R.string.home_message))
            }
            todoList?.let {
                for (todoItem in todoList) {
                    if (todoItem != null) {
                        Spacer(Modifier.height(24.dp))
                        Text(text = "${stringResource(id = R.string.fetched)} ${todoItem.todo} (${if (todoItem.completed) "done" else "pending"})")
                        Button(onClick = { onSubmit(todoItem.todo) }) {
                            Text(text = stringResource(id = R.string.goto_result_screen))
                        }
                    }
                }
            }
        }
    }
}
