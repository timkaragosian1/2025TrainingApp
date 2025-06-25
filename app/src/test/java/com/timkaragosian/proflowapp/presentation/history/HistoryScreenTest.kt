package com.timkaragosian.proflowapp.presentation.history

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.timkaragosian.proflowapp.di.testModule
import com.timkaragosian.proflowapp.domain.model.HistoryEntry
import com.timkaragosian.proflowapp.domain.usecase.history.ObserveHistoryUseCase
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.robolectric.annotation.Config

@Config(sdk = [34])
@RunWith(AndroidJUnit4::class)
class HistoryScreenTest {

    private lateinit var viewModel: HistoryViewModel
    private val observeHistoryUseCase:ObserveHistoryUseCase = mockk()

    @get:Rule
    val composeTestRule = createComposeRule()

    private val dummyHistory = listOf(
        HistoryEntry(0, "First Entry", 1719400000000),
        HistoryEntry(1, "Second Entry", 1719401000000)
    )

    @Before
    fun setup() {
        stopKoin()
        startKoin {
            androidContext(ApplicationProvider.getApplicationContext())
            modules(testModule)
        }
    }

    @After
    fun tearDown() {
        stopKoin()
    }

    @Test
    fun showsNoHistoryPlaceholderWhenEmpty() {
        coEvery { observeHistoryUseCase() } returns flowOf(emptyList())

        viewModel = HistoryViewModel(observeHistoryUseCase)

        composeTestRule.setContent {
            HistoryScreen(
                onNavigateBack = {},
                vm = viewModel
            )
        }

        composeTestRule.onNodeWithText("No history yet.").assertIsDisplayed()
    }

    @Test
    fun showsHistoryItemsWhenPresent() {
        coEvery { observeHistoryUseCase() } returns flowOf(dummyHistory)

        viewModel = HistoryViewModel(observeHistoryUseCase)

        composeTestRule.setContent {
            HistoryScreen(
                onNavigateBack = {},
                vm = viewModel
            )
        }

        composeTestRule.onNodeWithText("First Entry").assertIsDisplayed()
        composeTestRule.onNodeWithText("Second Entry").assertIsDisplayed()
    }

    @Test
    fun clickingBackButtonTriggersCallback() {
        coEvery { observeHistoryUseCase() } returns flowOf(dummyHistory)

        viewModel = HistoryViewModel(observeHistoryUseCase)

        var backClicked = false

        composeTestRule.setContent {
            HistoryScreen(
                onNavigateBack = { backClicked = true },
                vm = viewModel
            )
        }

        composeTestRule.onNodeWithContentDescription("Back").performClick()
        assert(backClicked)
    }
}