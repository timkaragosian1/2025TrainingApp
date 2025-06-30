package com.timkaragosian.proflowapp.presentation.flowresult

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.timkaragosian.proflowapp.data.network.TodoDto
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FlowResultScreen(
    todoDto: TodoDto,
    onNavigateBack: () -> Unit,
    vm: FlowResultViewModel = koinViewModel()
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        contentWindowInsets = WindowInsets.systemBars,
        topBar = {
            TopAppBar(
                title = { Text("History") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Default.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        FlowResultContent(
            todoDto = todoDto,
            onComplete = {
                vm.completeTodo(todoDto.id)
                onNavigateBack()
                         },
            onDelete = {
                vm.deleteTodo(todoDto.id)
                onNavigateBack()
                       },
            modifier = Modifier.padding(paddingValues)
        )
    }
}

@Composable
fun FlowResultContent(
    todoDto: TodoDto,
    onComplete: () -> Unit,
    onDelete: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.Start) {
            Text(text = "To Do Details")
            Spacer(modifier = Modifier.height(12.dp))
            Text(text = "Todo Task: ${todoDto.todo}")
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = "Completed: ${todoDto.completed}")
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = "Timestamp: ${todoDto.timestamp}")
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = onComplete) {
                Text(text = "Complete")
            }
            Spacer(modifier = Modifier.height(8.dp))
            Button(onClick = onDelete) {
                Text(text = "Delete")
            }
        }
    }
}