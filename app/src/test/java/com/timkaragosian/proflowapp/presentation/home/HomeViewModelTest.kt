package com.timkaragosian.proflowapp.presentation.home

import app.cash.turbine.test
import com.timkaragosian.proflowapp.data.network.TodoDto
import com.timkaragosian.proflowapp.domain.usecase.history.SaveHistoryUseCase
import com.timkaragosian.proflowapp.domain.usecase.home.AddItemUseCase
import com.timkaragosian.proflowapp.domain.usecase.home.GetTodoUseCase
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class HomeViewModelTest {

    private val testDispatcher = StandardTestDispatcher()

    private lateinit var getTodo: GetTodoUseCase
    private lateinit var addItem: AddItemUseCase
    private lateinit var saveHistory: SaveHistoryUseCase
    private lateinit var viewModel: HomeViewModel

    private val fakeTodos = listOf(
        TodoDto("1", "Test A", false, 1L),
        TodoDto("2", "Test B", true, 2L)
    )

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)

        getTodo = mockk {
            coEvery { this@mockk.invoke() } returns flowOf(fakeTodos)
        }
        addItem = mockk(relaxed = true)
        saveHistory = mockk(relaxed = true)

        viewModel = HomeViewModel(getTodo, addItem, saveHistory)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `loadTodoList emits todos`() = runTest {
        viewModel.onEvent(HomeUiEvent.LoadTodoList)
        testDispatcher.scheduler.advanceUntilIdle()

        viewModel.uiState.test {
            val state = awaitItem()
            assertEquals(fakeTodos, state.todoList)
        }
    }

    @Test
    fun `onTodoTextChange updates newTodoText`() = runTest {
        viewModel.onEvent(HomeUiEvent.TodoTextChanged("Write test"))
        viewModel.uiState.test {
            val state = awaitItem()
            assertEquals("Write test", state.newTodoText)
        }
    }

    @Test
    fun `onAddTodoClicked shows dialog`() = runTest {
        viewModel.onEvent(HomeUiEvent.AddTodoClicked)
        viewModel.uiState.test {
            val state = awaitItem()
            assertTrue(state.showAddDialog)
        }
    }

    @Test
    fun `onDismissDialog hides dialog`() = runTest {
        viewModel.onEvent(HomeUiEvent.AddTodoClicked)
        viewModel.onEvent(HomeUiEvent.DismissDialog)
        viewModel.uiState.test {
            val state = awaitItem()
            assertFalse(state.showAddDialog)
        }
    }

    @Test
    fun `onConfirmAddTodo adds item, saves history, resets state`() = runTest {
        viewModel.onEvent(HomeUiEvent.TodoTextChanged("Something"))
        viewModel.onEvent(HomeUiEvent.ConfirmAddTodo("Something"))
        testDispatcher.scheduler.advanceUntilIdle()

        coVerify { addItem(match { it.todo == "Something" }) }
        coVerify { saveHistory(match { it.contains("Inserted Todo Task: Something") }) }
    }

    @Test
    fun `NavigateToDetails emits event`() = runTest {
        viewModel.onEvent(HomeUiEvent.NavigateToDetails(fakeTodos[0]))
        viewModel.event.test {
            val event = awaitItem()
            assertEquals(HomeUiEvent.NavigateToDetails(fakeTodos[0]), event)
        }
    }

    @Test
    fun `Logout emits event`() = runTest {
        viewModel.onEvent(HomeUiEvent.Logout)
        viewModel.event.test {
            val event = awaitItem()
            assertEquals(HomeUiEvent.Logout, event)
        }
    }
}