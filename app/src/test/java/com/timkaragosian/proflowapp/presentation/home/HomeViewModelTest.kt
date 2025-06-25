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
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class HomeViewModelTest {
    private val testDispatcher: TestDispatcher = StandardTestDispatcher()

    private lateinit var viewModel: HomeViewModel
    private val getTodo: GetTodoUseCase = mockk()
    private val addItem: AddItemUseCase = mockk(relaxed = true)
    private val saveHistory: SaveHistoryUseCase = mockk(relaxed = true)

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        viewModel = HomeViewModel(
            getTodo = getTodo,
            addItem = addItem,
            saveHistory = saveHistory
        )
    }

    @After
    fun cleanup() {
        Dispatchers.resetMain()
    }

    @Test
    fun loadTodoListEmitsFilteredList() = runTest {
        val mockTodos = listOf(
            TodoDto(todo = "First", completed = false, timestamp = 0L, id = "0"),
            null,
            TodoDto(todo = "Second", completed = true, timestamp = 0L, id = "0")
        )
        coEvery { getTodo.invoke() } returns flowOf(mockTodos)

        viewModel.loadTodoList()
        testDispatcher.scheduler.advanceUntilIdle()

        viewModel.todoList.test {
            val emitted = awaitItem()
            assert(emitted.size == 2)
            assert(emitted[0].todo == "First")
            assert(emitted[1].todo == "Second")
            cancel()
        }
    }

    @Test
    fun addTodoItemViaAddItemUseCase() = runTest {
        val todo = TodoDto(todo = "New Item", completed = false,  timestamp = 0L, id = "0")

        viewModel.addTodoItem(todo)
        testDispatcher.scheduler.advanceUntilIdle()

        coVerify { addItem.invoke(todo) }
    }

    @Test
    fun insertHistoryOnActionViaSaveHistoryUseCase() = runTest {
        val text = "Clicked submit"

        viewModel.insertHistoryOnAction(text)
        testDispatcher.scheduler.advanceUntilIdle()

        coVerify { saveHistory.invoke(text) }
    }
}
