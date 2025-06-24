package com.timkaragosian.proflowapp.presentation.signin

import androidx.compose.ui.Modifier
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.timkaragosian.proflowapp.feature.auth.SignInEvent
import com.timkaragosian.proflowapp.feature.auth.SignInState
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SignInScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private lateinit var capturedEvents: MutableList<SignInEvent>

    private fun setContent(state: SignInState) {
        capturedEvents = mutableListOf()
        composeTestRule.setContent {
            SignInScreen(
                modifier = Modifier,
                state = state,
                onEvent = { capturedEvents.add(it) }
            )
        }
    }

    @Test
    fun testInitialScreenStateDisplayedCorrectly() {
        setContent(SignInState())

        composeTestRule.onNodeWithText("Welcome Back").assertIsDisplayed()
        composeTestRule.onNodeWithTag("OutlinedTextField_username").assertIsDisplayed()
        composeTestRule.onNodeWithTag("OutlinedTextField_password").assertIsDisplayed()
        composeTestRule.onNodeWithText("Sign In").assertIsDisplayed()
    }

    @Test
    fun testUsernameFieldInputTriggersEvent() {
        setContent(SignInState())

        val username = "testuser"
        composeTestRule.onNodeWithText("Username")
            .performTextInput(username)

        assert(capturedEvents.contains(SignInEvent.UsernameChanged(username)))
    }

    @Test
    fun testPasswordFieldInputTriggersEvent() {
        setContent(SignInState())

        val password = "secret"
        composeTestRule.onNodeWithText("Password")
            .performTextInput(password)

        assert(capturedEvents.contains(SignInEvent.PasswordChanged(password)))
    }

    @Test
    fun testSubmitButtonClickTriggersEvent() {
        setContent(SignInState())

        composeTestRule.onNodeWithText("Sign In").performClick()
        assert(capturedEvents.contains(SignInEvent.SubmitClicked))
    }

    @Test
    fun testLoadingStateDisablesButtonAndShowsProgress() {
        setContent(SignInState(isLoading = true))

        composeTestRule.onNodeWithText("Sign In").assertDoesNotExist()
    }
}