package com.timkaragosian.proflowapp.presentation.signin

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.timkaragosian.proflowapp.R
import com.timkaragosian.proflowapp.data.resourcesprovider.FlowAppResourceProvider
import com.timkaragosian.proflowapp.domain.auth.AuthRepository
import com.timkaragosian.proflowapp.feature.auth.SignInEffect
import com.timkaragosian.proflowapp.feature.auth.SignInEvent
import com.timkaragosian.proflowapp.feature.auth.SignInState
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


class SignInViewModel(
    private val strings:FlowAppResourceProvider,
    private val repo: AuthRepository
):ViewModel() {

    //UI state
    private val _state = MutableStateFlow(SignInState())
    val state: StateFlow<SignInState> = _state.asStateFlow()

    //One-off Effects
    private val _effect = Channel<SignInEffect>(Channel.BUFFERED)
    val effect = _effect.receiveAsFlow()

    fun onEvent(event:SignInEvent) {
        when (event) {
            is SignInEvent.UsernameChanged -> _state.update { it.copy(username = event.value) }
            is SignInEvent.PasswordChanged -> _state.update { it.copy(password = event.value) }
            SignInEvent.SubmitClicked -> submitSignIn()
        }
    }
    private fun submitSignIn() = viewModelScope.launch {
        val (user,pass) = state.value.let { it.username to it.password }
        if (user.isBlank() || pass.isBlank()) {
            _effect.send(SignInEffect.ShowError(strings.string(R.string.both_fields_error)))
            return@launch
        }

        if (!validateUsername(user)) {
            _effect.send(SignInEffect.ShowError(strings.string(R.string.username_invalid_error)))
            return@launch
        }

        if (!validatePassword(pass)) {
            _effect.send(SignInEffect.ShowError(strings.string(R.string.pass_invalid_error)))
            return@launch
        }

        _state.update { it.copy(isLoading = true) }

        repo.login(user,pass, strings)
            .onSuccess { _effect.send(SignInEffect.NavigateToHome) }
            .onFailure { state.value.errorMessage = mutableStateOf(strings.string(R.string.login_failed_error)) }//_effect.send(SignInEffect.ShowError(it.message ?: "Login failed")) }

        _state.update { it.copy(isLoading = false) }
    }
}