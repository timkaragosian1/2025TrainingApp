package com.timkaragosian.proflowapp.presentation.signin

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.timkaragosian.proflowapp.R
import com.timkaragosian.proflowapp.feature.auth.SignInEvent
import com.timkaragosian.proflowapp.feature.auth.SignInState

@Composable
fun SignInScreen(
    modifier: Modifier,
    state: SignInState,
    onEvent: (SignInEvent) -> Unit,
) {
    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        snackbarHost = { SnackbarHost(hostState = snackbarHostState)}
    ){ padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 24.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Welcome Back",
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(16.dp))
            Image(
                painter = painterResource(id = R.drawable.ic_launcher_background),
                contentDescription = "",
                modifier = Modifier.height(160.dp)
            )
            Spacer(modifier = Modifier.height(32.dp))

            OutlinedTextField(
                value = state.username,
                onValueChange = { onEvent(SignInEvent.UsernameChanged(it)) },
                label = { Text(text = stringResource(id = R.string.username)) },
                singleLine = true,
                leadingIcon = { Icon(Icons.Default.Person, null) },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = state.password,
                onValueChange = { onEvent(SignInEvent.PasswordChanged(it)) },
                label = { Text(text = stringResource(id = R.string.password)) },
                singleLine = true,
                leadingIcon = { Icon(Icons.Default.Lock, null) },
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(24.dp))
            Button(
                onClick = { onEvent(SignInEvent.SubmitClicked) },
                enabled = !state.isLoading,
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier.fillMaxWidth(),
            ){
                if(state.isLoading){
                    CircularProgressIndicator(
                        strokeWidth = 2.dp,
                        modifier = Modifier.size(20.dp)
                    )
                } else {
                    Text(text = stringResource(id = R.string.sign_in_btn))
                }
            }
        }
    }
}
