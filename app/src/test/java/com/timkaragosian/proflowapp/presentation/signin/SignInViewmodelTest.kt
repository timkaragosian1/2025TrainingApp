package com.timkaragosian.proflowapp.presentation.signin

import app.cash.turbine.test
import com.timkaragosian.proflowapp.R
import com.timkaragosian.proflowapp.data.resourcesprovider.FlowAppResourceProvider
import com.timkaragosian.proflowapp.domain.auth.AuthRepository
import com.timkaragosian.proflowapp.feature.auth.SignInEffect
import com.timkaragosian.proflowapp.feature.auth.SignInEvent
import io.mockk.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class SignInViewModelTest {

    private val dispatcher = StandardTestDispatcher()

    private lateinit var repo: AuthRepository
    private lateinit var strings: FlowAppResourceProvider
    private lateinit var vm: SignInViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(dispatcher)

        repo = mockk()
        strings = mockk()

        // generic string stubs
        every { strings.string(any(), *anyVararg()) } answers {
            val resId = firstArg<Int>()
            "str_$resId"
        }

        vm = SignInViewModel(strings, repo)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    // ---------- helper ----------
    private fun advance() = dispatcher.scheduler.advanceUntilIdle()

    // ---------- tests ------------

    @Test
    fun emptyFields_showsBothFieldsError() = runTest(dispatcher) {
        vm.effect.test {
            vm.onEvent(SignInEvent.SubmitClicked)
            advance()

            assertEquals(
                SignInEffect.ShowError("str_${R.string.both_fields_error}"),
                awaitItem()
            )
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun invalidUsername_showsUsernameError() = runTest(dispatcher) {
        vm.onEvent(SignInEvent.UsernameChanged("ab"))
        vm.onEvent(SignInEvent.PasswordChanged("Valid1!X"))

        vm.effect.test {
            vm.onEvent(SignInEvent.SubmitClicked)
            advance()

            assertEquals(
                SignInEffect.ShowError("str_${R.string.username_invalid_error}"),
                awaitItem()
            )
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun invalidPassword_showsPasswordError() = runTest(dispatcher) {
        vm.onEvent(SignInEvent.UsernameChanged("validUser"))
        vm.onEvent(SignInEvent.PasswordChanged("short1!"))

        vm.effect.test {
            vm.onEvent(SignInEvent.SubmitClicked)
            advance()

            assertEquals(
                SignInEffect.ShowError("str_${R.string.pass_invalid_error}"),
                awaitItem()
            )
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun success_login_navigatesHome_andStopsLoading() = runTest(dispatcher) {
        // arrange
        coEvery { repo.login(any(), any(), any()) } returns Result.success(Unit)

        vm.onEvent(SignInEvent.UsernameChanged("admin"))
        vm.onEvent(SignInEvent.PasswordChanged("Letme1n!"))

        vm.effect.test {
            vm.onEvent(SignInEvent.SubmitClicked)

            advance()

            // effect NavigateToHome
            assertEquals(SignInEffect.NavigateToHome, awaitItem())
            // loading reset
            assertFalse(vm.state.value.isLoading)

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun failure_login_showsLoginFailed_andStopsLoading() = runTest(dispatcher) {
        // arrange
        coEvery { repo.login(any(), any(), any()) } returns
                Result.failure(RuntimeException("boom"))

        vm.onEvent(SignInEvent.UsernameChanged("validUser"))
        vm.onEvent(SignInEvent.PasswordChanged("Valid1!X"))

        vm.effect.test {
            vm.onEvent(SignInEvent.SubmitClicked)

            advance()

            assertEquals(R.string.login_failed_error, vm.state.value.errorMessageId.value)
            assertFalse(vm.state.value.isLoading)

            cancelAndIgnoreRemainingEvents()
        }
    }
}
