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
    fun onTodoTextChangeUpdatesState() {
        vm.onTodoTextChange("Do something")
        assertEquals("Do something", vm.newTodoText.value)
    }

    @Test
    fun onAddTodoClickedShowsDialog() {
        vm.onAddTodoClicked()
        assertEquals(true, vm.showAddDialog.value)
    }

    @Test
    fun onDismissDialogHidesDialog() {
        vm.onAddTodoClicked()
        vm.onDismissDialog()
        assertEquals(false, vm.showAddDialog.value)
    }

    @Test
    fun onConfirmAddTodoAddsSavesClears() = runTest(testDispatcher) {
        vm.onTodoTextChange("New Task")
        vm.onConfirmAddTodo()

        advanceUntilIdle()

        coVerify { addItem(any()) }
        coVerify { saveHistory(match { it.contains("Inserted Todo Task") }) }
        assertEquals("", vm.newTodoText.value)
        assertEquals(false, vm.showAddDialog.value)
    }

    @Test
    fun insertHistoryOnActionTriggersSave() = runTest(testDispatcher) {
        val action = "Some action occurred"
        vm.insertHistoryOnAction(action)
        advanceUntilIdle()
        coVerify { saveHistory(action) }
    }

    @Test
    fun loadTodoListFiltersOutNulls() = runTest(testDispatcher) {
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
