package com.timkaragosian.proflowapp.presentation.home

import com.timkaragosian.proflowapp.data.network.TodoDto
import com.timkaragosian.proflowapp.domain.usecase.history.SaveHistoryUseCase
import com.timkaragosian.proflowapp.domain.usecase.home.AddItemUseCase
import com.timkaragosian.proflowapp.domain.usecase.home.GetTodoUseCase
import io.mockk.coVerify
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class HomeViewModelTest {

    private val getTodo: GetTodoUseCase = mockk(relaxed = true)
    private val addItem: AddItemUseCase = mockk(relaxed = true)
    private val saveHistory: SaveHistoryUseCase = mockk(relaxed = true)

    private lateinit var vm: HomeViewModel

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        vm = HomeViewModel(getTodo, addItem, saveHistory)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `onTodoTextChange updates state`() {
        vm.onTodoTextChange("Do something")
        assertEquals("Do something", vm.newTodoText.value)
    }

    @Test
    fun `onAddTodoClicked shows dialog`() {
        vm.onAddTodoClicked()
        assertEquals(true, vm.showAddDialog.value)
    }

    @Test
    fun `onDismissDialog hides dialog`() {
        vm.onAddTodoClicked()
        vm.onDismissDialog()
        assertEquals(false, vm.showAddDialog.value)
    }

    @Test
    fun `onConfirmAddTodo adds todo, saves history, and clears input`() = runTest(testDispatcher) {
        vm.onTodoTextChange("New Task")
        vm.onConfirmAddTodo()

        advanceUntilIdle()

        coVerify { addItem(any()) }
        coVerify { saveHistory(match { it.contains("Inserted Todo Task") }) }
        assertEquals("", vm.newTodoText.value)
        assertEquals(false, vm.showAddDialog.value)
    }

    @Test
    fun `insertHistoryOnAction triggers save`() = runTest(testDispatcher) {
        val action = "Some action occurred"
        vm.insertHistoryOnAction(action)
        advanceUntilIdle()
        coVerify { saveHistory(action) }
    }

    @Test
    fun `loadTodoList filters out nulls`() = runTest(testDispatcher) {
        val listWithNulls = listOf(
            TodoDto("1", "Task A", false, 0),
            null,
            TodoDto("2", "Task B", true, 1)
        )
        io.mockk.coEvery { getTodo() } returns flowOf(listWithNulls)

        vm.loadTodoList()
        advanceUntilIdle()

        assertEquals(2, vm.todoList.value.size)
        assertEquals("1", vm.todoList.value[0].id)
    }
}
