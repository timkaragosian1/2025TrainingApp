package com.timkaragosian.proflowapp.presentation.flowresult

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.timkaragosian.proflowapp.data.network.TodoDto
import com.timkaragosian.proflowapp.di.testModule
import com.timkaragosian.proflowapp.domain.usecase.flowresult.CompleteTodoTaskUseCase
import com.timkaragosian.proflowapp.domain.usecase.flowresult.DeleteTodoTaskUseCase
import io.mockk.mockk
import io.mockk.verify
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.runner.RunWith
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import kotlin.test.Test

@Config(sdk = [34])
@RunWith(RobolectricTestRunner::class)
class FlowResultScreenKoinTest : KoinTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private val testTodo = TodoDto(
        id = "test-id",
        todo = "Write tests",
        completed = false,
        timestamp = 1650000000L
    )

    private val mockViewModel = mockk<FlowResultViewModel>(relaxed = true)
    private val mockDeleteTodoTaskUseCase = mockk<DeleteTodoTaskUseCase>(relaxed = true)
    private val mockCompleteTodoTaskUseCase = mockk<CompleteTodoTaskUseCase>(relaxed = true)

    @Before
    fun setup(){
        stopKoin()
        startKoin {
            modules(module {
                viewModel { mockViewModel }
            })
        }
    }

    @After
    fun teardown(){
        stopKoin()
    }

    @Test
    fun clickingComplete_invokesViewModel() {
        composeTestRule.setContent {
            FlowResultScreen(
                todoDto = testTodo,
                onNavigateBack = {},
                vm = mockViewModel
            )
        }

        composeTestRule.onNodeWithText("Complete").performClick()

        verify { mockViewModel.completeTodo("test-id") }

        stopKoin()
    }

    @Test
    fun clickingDelete_invokesViewModel() {
        composeTestRule.setContent {
            FlowResultScreen(
                todoDto = testTodo,
                onNavigateBack = {},
                vm = mockViewModel
            )
        }

        composeTestRule.onNodeWithText("Delete").performClick()

        verify { mockViewModel.deleteTodo("test-id") }
    }

    @Test
    fun clickingBack_invokesCallback() {
        var backClicked = false

        composeTestRule.setContent {
            FlowResultScreen(
                todoDto = testTodo,
                onNavigateBack = { backClicked = true },
                vm = mockViewModel
            )
        }

        composeTestRule.onNodeWithContentDescription("Back").performClick()
        assert(backClicked)
    }

    @Test
    fun displaysCorrectTodoContent() {
        composeTestRule.setContent {
            FlowResultScreen(
                todoDto = testTodo,
                onNavigateBack = {},
                vm = mockViewModel
            )
        }

        composeTestRule.onNodeWithText("To Do Details").assertIsDisplayed()
        composeTestRule.onNodeWithText("Todo Task: Write tests").assertIsDisplayed()
        composeTestRule.onNodeWithText("Completed: false").assertIsDisplayed()
        composeTestRule.onNodeWithText("Timestamp: 1650000000").assertIsDisplayed()
    }
}
