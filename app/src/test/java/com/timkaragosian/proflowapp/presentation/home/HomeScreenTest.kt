package com.timkaragosian.proflowapp.presentation.home

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextContains
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.timkaragosian.proflowapp.data.network.TodoDto
import com.timkaragosian.proflowapp.di.testModule
import com.timkaragosian.proflowapp.domain.usecase.flowresult.CompleteTodoTaskUseCase
import com.timkaragosian.proflowapp.domain.usecase.flowresult.DeleteTodoTaskUseCase
import com.timkaragosian.proflowapp.domain.usecase.history.ObserveHistoryUseCase
import com.timkaragosian.proflowapp.domain.usecase.history.SaveHistoryUseCase
import com.timkaragosian.proflowapp.domain.usecase.home.AddItemUseCase
import com.timkaragosian.proflowapp.domain.usecase.home.GetTodoUseCase
import com.timkaragosian.proflowapp.presentation.flowresult.FlowResultViewModel
import com.timkaragosian.proflowapp.presentation.history.HistoryViewModel
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import org.robolectric.annotation.Config

@Config(sdk = [34])
@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class HomeScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private val dummyTodo = TodoDto(
        id = "123",
        todo = "Sample task",
        completed = false,
        timestamp = 1234567890L
    )

    private val getTodo = mockk<GetTodoUseCase>()
    private val addItem = mockk<AddItemUseCase>(relaxed = true)
    private val saveHistory = mockk<SaveHistoryUseCase>(relaxed = true)

    private lateinit var viewModel: HomeViewModel

    @Before
    fun setup() {
        stopKoin()
        startKoin {
            androidContext(ApplicationProvider.getApplicationContext())
            modules(homeTestModule)
        }
        Dispatchers.setMain(Dispatchers.Unconfined)
        coEvery { getTodo() } returns flowOf(listOf(dummyTodo))
        viewModel = HomeViewModel(getTodo, addItem, saveHistory)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        stopKoin()
    }

    @Test
    fun displaysTitleAndButtonsCorrectly() {
        composeTestRule.setContent {
            HomeScreen(
                vm = viewModel,
                onTaskResults = { _, _, _, _ -> },
                onNavigateToHistory = {}
            )
        }

        composeTestRule.onNodeWithTag("home_title").assertIsDisplayed()
        composeTestRule.onNodeWithTag("load_button").assertIsDisplayed()
        composeTestRule.onNodeWithTag("fab_add_todo").assertIsDisplayed()
        composeTestRule.onNodeWithTag("history_button").assertIsDisplayed()
    }

    @Test
    fun clickingFabShowsDialog() {
        composeTestRule.setContent {
            HomeScreen(
                vm = viewModel,
                onTaskResults = { _, _, _, _ -> },
                onNavigateToHistory = {}
            )
        }

        composeTestRule.onNodeWithTag("fab_add_todo").performClick()
        composeTestRule.onNodeWithTag("todo_input").assertIsDisplayed()
    }

    @Test
    fun showsTodoListAndHandlesSubmitClick() {
        var resultTriggered = false

        composeTestRule.runOnIdle {
            viewModel.loadTodoList()
        }

        composeTestRule.setContent {
            HomeScreen(
                vm = viewModel,
                onTaskResults = { _, _, _, _ -> resultTriggered = true },
                onNavigateToHistory = {}
            )
        }

        composeTestRule.onNodeWithTag("todo_text_0").assertTextContains("Sample task", substring = true)
        composeTestRule.onNodeWithTag("submit_button_0").performClick()
        assert(resultTriggered)
    }

    @Test
    fun historyButtonTriggersCallback() {
        var historyClicked = false

        composeTestRule.setContent {
            HomeScreen(
                vm = viewModel,
                onTaskResults = { _, _, _, _ -> },
                onNavigateToHistory = { historyClicked = true }
            )
        }

        composeTestRule.onNodeWithTag("history_button").performClick()
        assert(historyClicked)
    }

    val homeTestModule = module {
        single<GetTodoUseCase> { mockk(relaxed = true) }
        single<AddItemUseCase> { mockk(relaxed = true) }
        single<SaveHistoryUseCase> { mockk(relaxed = true) }

        viewModel {
            HomeViewModel(
                getTodo = get(),
                addItem = get(),
                saveHistory = get()
            )
        }
    }
}