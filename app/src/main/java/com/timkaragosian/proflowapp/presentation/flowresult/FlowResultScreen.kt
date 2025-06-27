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
import androidx.compose.material.icons.filled.ArrowBack
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import com.timkaragosian.proflowapp.R
import com.timkaragosian.proflowapp.data.network.TodoDto
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FlowResultScreen(
    todoDto: TodoDto,
    onNavigateBack: () -> Unit,
    viewModel: FlowResultViewModel = koinViewModel()
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        contentWindowInsets = WindowInsets.systemBars,
        topBar = {
            TopAppBar(
                title = { Text("History") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.AutoMirrored.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column {
                Text("To Do Details")
                Spacer(modifier = Modifier.height(12.dp))
                Text("Todo Task: ${todoDto.todo}")
                Spacer(modifier = Modifier.height(2.dp))
                Text("Completed: ${todoDto.completed}")
                Spacer(modifier = Modifier.height(2.dp))
                Text("Timestamp: ${todoDto.timestamp}")
                Spacer(modifier = Modifier.height(12.dp))
                Button(onClick = {
                    viewModel.completeTodo(todoDto.id)
                    onNavigateBack()
                }) {
                    Text(text = "Complete")
                }
                Spacer(modifier = Modifier.height(5.dp))
                Button(onClick = {
                    viewModel.deleteTodo(todoDto.id)
                    onNavigateBack()
                }) {
                    Text(text = "Delete")
                }
            }
        }
    }
}
