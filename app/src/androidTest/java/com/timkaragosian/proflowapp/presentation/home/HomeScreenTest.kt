package com.timkaragosian.proflowapp.presentation.home

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextContains
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import com.timkaragosian.proflowapp.data.network.TodoDto
import junit.framework.TestCase.assertTrue
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.testng.annotations.BeforeTest

@MediumTest
@RunWith(AndroidJUnit4::class)
class HomeScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private val capturedHistory = mutableListOf<String>()
    private val capturedTasks = mutableListOf<TodoDto>()
    private var navigatedToHistory = false
    private var loggedOut = false
    private var navigatedToTask: TodoDto? = null
    private var textChanged: String? = null
    private var confirmAdd: String? = null
    private var dialogDismissed = false
    private var loadTriggered = false

    @BeforeTest
    fun resetState() {
        capturedHistory.clear()
        capturedTasks.clear()
        navigatedToHistory = false
        loggedOut = false
        navigatedToTask = null
        textChanged = null
        confirmAdd = null
        dialogDismissed = false
        loadTriggered = false
    }

    @Test
    fun titleAndButtonsDisplayedCorrectly() {
        composeTestRule.setContent {
            HomeScreen(
                state = HomeUiState(),
                getTodoList = { loadTriggered = true },
                insertHistoryOnAction = { capturedHistory.add(it) },
                onNavigateToHistory = { navigatedToHistory = true },
                onLogout = { loggedOut = true },
                onNavigateToTaskDetails = { id, todo, completed, timestamp ->
                    navigatedToTask = TodoDto(id, todo, completed, timestamp)
                },
                onTodoTextChange = { textChanged = it },
                onAddTodoClicked = {},
                onConfirmAddTodo = { confirmAdd = it },
                onDismissDialog = { dialogDismissed = true }
            )
        }

        composeTestRule.onNodeWithTag("home_title").assertIsDisplayed()
        composeTestRule.onNodeWithTag("history_button").assertIsDisplayed()
        composeTestRule.onNodeWithTag("logout_button").assertIsDisplayed()
        composeTestRule.onNodeWithTag("fab_add_todo").assertIsDisplayed()
        composeTestRule.onNodeWithTag("load_button").assertIsDisplayed()
    }

    @Test
    fun todoListItemsAreDisplayed() {
        val todos = listOf(
            TodoDto("1", "Test A", false, 1L),
            TodoDto("2", "Test B", true, 2L)
        )

        composeTestRule.setContent {
            HomeScreen(
                state = HomeUiState(todoList = todos),
                getTodoList = {},
                insertHistoryOnAction = {},
                onNavigateToHistory = {},
                onLogout = {},
                onNavigateToTaskDetails = { id, todo, completed, timestamp ->
                    navigatedToTask = TodoDto(id, todo, completed, timestamp)
                },
                onTodoTextChange = {},
                onAddTodoClicked = {},
                onConfirmAddTodo = {},
                onDismissDialog = {}
            )
        }

        composeTestRule.onNodeWithTag("todo_text_0").assertTextContains("Test A", substring = true)
        composeTestRule.onNodeWithTag("todo_text_1").assertTextContains("Test B", substring = true)

        composeTestRule.onNodeWithTag("submit_button_0").performClick()
        assertTrue(navigatedToTask?.id == "1")
    }

    @Test
    fun addDialogShowsAndHandlesInputs() {
        composeTestRule.setContent {
            HomeScreen(
                state = HomeUiState(showAddDialog = true, newTodoText = "Some Task"),
                getTodoList = {},
                insertHistoryOnAction = { capturedHistory.add(it) },
                onNavigateToHistory = {},
                onLogout = {},
                onNavigateToTaskDetails = { _, _, _, _ -> },
                onTodoTextChange = { textChanged = it },
                onAddTodoClicked = {},
                onConfirmAddTodo = { confirmAdd = it },
                onDismissDialog = { dialogDismissed = true }
            )
        }

        composeTestRule.onNodeWithTag("todo_input").assertExists()
        composeTestRule.onNodeWithText("Add").performClick()

        assertTrue(confirmAdd == "Some Task")
        assertTrue(capturedHistory.contains("Some Task"))
    }
}
