package com.timkaragosian.proflowapp.presentation.history

import com.timkaragosian.proflowapp.domain.model.HistoryEntry
import com.timkaragosian.proflowapp.domain.usecase.history.ObserveHistoryUseCase
import io.mockk.every
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestWatcher
import org.junit.runner.Description

@OptIn(ExperimentalCoroutinesApi::class)
class HistoryViewModelTest {

    @get:Rule
    val coroutinesRule = MainDispatcherRule() // custom rule for Dispatchers.setMain

    private val testHistory = listOf(
        HistoryEntry(0,"Task 1", 111L),
        HistoryEntry(1, "Task 2", 222L),
    )

    private val fakeFlow = flowOf(testHistory)

    @Test
    fun historyStateEmitsExpectedEntries() = runTest {
        val observeHistory = mockk<ObserveHistoryUseCase>()
        every { observeHistory() } returns fakeFlow

        val viewModel = HistoryViewModel(observeHistory)

        // collect single value
        val actual = viewModel.history.first()

        assertEquals(testHistory, actual)
    }
}

@ExperimentalCoroutinesApi
class MainDispatcherRule(
    val dispatcher: TestDispatcher = UnconfinedTestDispatcher()
): TestWatcher() {

    override fun starting(description: Description) {
        Dispatchers.setMain(dispatcher)
    }

    override fun finished(description: Description) {
        Dispatchers.resetMain()
    }
}