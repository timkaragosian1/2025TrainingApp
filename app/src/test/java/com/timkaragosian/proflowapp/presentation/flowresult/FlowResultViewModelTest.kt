package com.timkaragosian.proflowapp.presentation.flowresult

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.timkaragosian.proflowapp.domain.usecase.flowresult.CompleteTodoTaskUseCase
import com.timkaragosian.proflowapp.domain.usecase.flowresult.DeleteTodoTaskUseCase
import io.mockk.clearAllMocks
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.rules.TestWatcher
import org.junit.runner.Description
import kotlin.test.Test

@OptIn(ExperimentalCoroutinesApi::class)
class FlowResultViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val dispatcherRule = MainDispatcherRule()

    private val deleteTodoTaskUseCase = mockk<DeleteTodoTaskUseCase>(relaxed = true)
    private val completeTodoTaskUseCase = mockk<CompleteTodoTaskUseCase>(relaxed = true)

    private lateinit var viewModel: FlowResultViewModel

    @Before
    fun setup() {
        viewModel = FlowResultViewModel(
            deleteTodoTaskUseCase = deleteTodoTaskUseCase,
            completeTodoTaskUseCase = completeTodoTaskUseCase
        )
    }

    @After
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun `deleteTodo calls use case with correct id`() = runTest {
        val id = "task-123"

        viewModel.deleteTodo(id)

        coVerify { deleteTodoTaskUseCase(id) }
    }

    @Test
    fun `completeTodo calls use case with correct id`() = runTest {
        val id = "task-456"

        viewModel.completeTodo(id)

        coVerify { completeTodoTaskUseCase(id) }
    }
}

@OptIn(ExperimentalCoroutinesApi::class)
class MainDispatcherRule(
    private val testDispatcher: TestDispatcher = UnconfinedTestDispatcher()
) : TestWatcher() {

    override fun starting(description: Description) {
        Dispatchers.setMain(testDispatcher)
    }

    override fun finished(description: Description) {
        Dispatchers.resetMain()
    }
}