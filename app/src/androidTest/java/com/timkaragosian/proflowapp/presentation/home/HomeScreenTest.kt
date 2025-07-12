package com.timkaragosian.proflowapp.presentation.home

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.timkaragosian.proflowapp.data.network.TodoDto
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class HomeScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private val dummyTodos = listOf(
        TodoDto("1", "Write Tests", false, 1234L),
        TodoDto("2", "Fix Bugs", true, 5678L)
    )

    private var capturedEvents = mutableListOf<HomeUiEvent>()

    private fun setContent(state: HomeUiState) {
        capturedEvents.clear()
        composeTestRule.setContent {
            HomeScreen(
                state = state,
                onEvent = { capturedEvents.add(it) }
            )
        }
    }

    @Test
    fun homeScreen_displaysInitialContent() {
        setContent(HomeUiState())

        composeTestRule.onNodeWithTag("home_title").assertIsDisplayed()
        composeTestRule.onNodeWithTag("load_button").assertIsDisplayed()
        composeTestRule.onNodeWithTag("history_button").assertIsDisplayed()
        composeTestRule.onNodeWithTag("logout_button").assertIsDisplayed()
        composeTestRule.onNodeWithTag("fab_add_todo").assertIsDisplayed()
    }

    @Test
    fun todoItems_renderCorrectlyAndTriggerNavigation() {
        setContent(HomeUiState(todoList = dummyTodos))

        composeTestRule.onNodeWithTag("todo_text_0").assertTextContains("Write Tests", substring = true)
        composeTestRule.onNodeWithTag("todo_text_1").assertTextContains("Fix Bugs", substring = true)
        composeTestRule.onNodeWithTag("submit_button_0").performClick()
        composeTestRule.waitForIdle()

        composeTestRule.onNodeWithTag("todo_text_0")
            .assertTextContains("Write Tests (pending)", substring = true)
    }

    @Test
    fun clickingFab_showsDialog() {
        setContent(HomeUiState())

        composeTestRule.onNodeWithTag("fab_add_todo").performClick()
        assert(capturedEvents.contains(HomeUiEvent.AddTodoClicked))
    }

    @Test
    fun dialog_renders_and_add_button_works() {
        setContent(HomeUiState(showAddDialog = true, newTodoText = "New Task"))

        composeTestRule.onNodeWithTag("todo_input").assertIsDisplayed()
        composeTestRule.onNodeWithText("Add").performClick()

        assert(capturedEvents.contains(HomeUiEvent.ConfirmAddTodo("New Task")))
    }

    @Test
    fun dialog_input_changes_triggerEvent() {
        setContent(HomeUiState(showAddDialog = true, newTodoText = ""))

        composeTestRule.onNodeWithTag("todo_input").performTextInput("Learn Compose")
        assert(capturedEvents.contains(HomeUiEvent.TodoTextChanged("Learn Compose")))
    }

    @Test
    fun history_and_logout_buttons_triggerEvents() {
        setContent(HomeUiState())

        composeTestRule.onNodeWithTag("history_button").performClick()
        composeTestRule.onNodeWithTag("logout_button").performClick()

        assert(capturedEvents.contains(HomeUiEvent.NavigateToHistory))
        assert(capturedEvents.contains(HomeUiEvent.Logout))
    }
}
