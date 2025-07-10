package com.timkaragosian.proflowapp.presentation.home

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.timkaragosian.proflowapp.MainActivity
import com.timkaragosian.proflowapp.data.network.TodoDto
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


//@RunWith(AndroidJUnit4::class)
//class HomeScreenInstrumentTest {
//
//    @get:Rule
//    val composeTestRule = createAndroidComposeRule<MainActivity>()
//
//    private lateinit var capturedEvents: MutableList<HomeUiEvent>
//
//    private fun setContent(state: HomeUiState) {
//        capturedEvents = mutableListOf()
//        composeTestRule.setContent {
//            HomeScreen(
//                state = state,
//                onEvent = { capturedEvents.add(it) }
//            )
//        }
//    }
//
//    @Before
//    fun reset() {
//        capturedEvents.clear()
//    }
//
//    @Test
//    fun titleAndButtonsDisplayedCorrectly() {
//        setContent(HomeUiState())
//
//        composeTestRule.onNodeWithTag("home_title").assertIsDisplayed()
//        composeTestRule.onNodeWithTag("fab_add_todo").assertIsDisplayed()
//        composeTestRule.onNodeWithTag("load_button").assertIsDisplayed()
//        composeTestRule.onNodeWithTag("logout_button").assertIsDisplayed()
//        composeTestRule.onNodeWithTag("history_button").assertIsDisplayed()
//    }
//
//    @Test
//    fun todoListItemsDisplayedCorrectly() {
//        val todos = listOf(
//            TodoDto("1", "Test A", false, 1L),
//            TodoDto("2", "Test B", true, 2L)
//        )
//
//        setContent(HomeUiState(todoList = todos))
//
//        composeTestRule.onNodeWithTag("todo_text_0").assertIsDisplayed()
//        composeTestRule.onNodeWithTag("todo_text_1").assertIsDisplayed()
//
//        composeTestRule.onNodeWithTag("submit_button_0").performClick()
//
//        assert(capturedEvents.contains(HomeUiEvent.NavigateToDetails(todos[0])))
//    }
//
//    @Test
//    fun addDialogShowsAndHandlesInput() {
//        setContent(HomeUiState(showAddDialog = true, newTodoText = "New Task"))
//
//        composeTestRule.onNodeWithTag("todo_input").assertIsDisplayed()
//        composeTestRule.onNodeWithText("Add").performClick()
//
//        assert(capturedEvents.contains(HomeUiEvent.ConfirmAddTodo("New Task")))
//    }
//
//    @Test
//    fun addTodoFabTriggersDialogEvent() {
//        setContent(HomeUiState())
//        composeTestRule.onNodeWithTag("fab_add_todo").performClick()
//
//        assert(capturedEvents.contains(HomeUiEvent.AddTodoClicked))
//    }
//
//    @Test
//    fun navigationButtonsTriggerEvents() {
//        setContent(HomeUiState())
//
//        composeTestRule.onNodeWithTag("logout_button").performClick()
//        composeTestRule.onNodeWithTag("history_button").performClick()
//
//        assert(capturedEvents.contains(HomeUiEvent.Logout))
//        assert(capturedEvents.contains(HomeUiEvent.NavigateToHistory))
//    }
//}