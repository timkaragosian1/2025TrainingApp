package com.timkaragosian.proflowapp.feature.auth

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import kotlinx.coroutines.flow.MutableStateFlow

//holder to be consumed by UI
data class SignInState(
    val username: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
    var errorMessage: MutableState<String?> = mutableStateOf(null),
)

//User intents coming from UI Layer
sealed interface SignInEvent {
    data class UsernameChanged(val value:String): SignInEvent
    data class PasswordChanged(val value:String): SignInEvent
    object SubmitClicked: SignInEvent
}

//side-effects the ViewModel asks the UI to perform (navigation, snackbar, dialog, etc)
sealed interface SignInEffect {
    object NavigateToHome: SignInEffect
    data class ShowError(val msg: String): SignInEffect
}