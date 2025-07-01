package com.timkaragosian.proflowapp.presentation.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.timkaragosian.proflowapp.data.network.TodoDto
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeScreenContainer(
    vm: HomeViewModel = koinViewModel(),
    onNavigateToHistory: () -> Unit,
    onNavigateToDetails: (TodoDto) -> Unit,
    onLogout: () -> Unit
) {
    val state by vm.uiState.collectAsState()
    val event by vm.event.collectAsState(initial = null)

    LaunchedEffect(event) {
        when (val e = event) {
            is HomeUiEvent.NavigateToDetails -> onNavigateToDetails(e.todo)
            is HomeUiEvent.NavigateToHistory -> onNavigateToHistory()
            is HomeUiEvent.Logout -> onLogout()
            else -> Unit
        }
    }

    HomeScreen(
        state = state,
        onEvent = vm::onEvent
    )
}