package com.timkaragosian.proflowapp.presentation.signin

import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.timkaragosian.proflowapp.feature.auth.SignInEffect
import org.koin.androidx.compose.koinViewModel

@Composable
fun SignInRoute(
    viewModel: SignInViewModel = koinViewModel(),
    onNavigationHome: () -> Unit
) {
    val state by viewModel.state.collectAsState()
    val snackbarHostState = remember {
        SnackbarHostState()
    }

    LaunchedEffect(Unit) {
        viewModel.effect.collect{effect ->
            when (effect){
                is SignInEffect.ShowError -> snackbarHostState.showSnackbar(message = effect.msg)
                SignInEffect.NavigateToHome -> onNavigationHome()
            }
        }
    }
    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        modifier = Modifier.imePadding()
    ) { padding ->
        SignInScreen(
            modifier = Modifier.padding(padding),
            state = state,
            onEvent = viewModel::onEvent,
        )
    }
}