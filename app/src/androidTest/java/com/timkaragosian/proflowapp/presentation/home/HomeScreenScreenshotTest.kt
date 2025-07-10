package com.timkaragosian.proflowapp.presentation.home

import androidx.test.ext.junit.runners.AndroidJUnit4

import org.junit.Rule
import org.junit.runner.RunWith


//@RunWith(AndroidJUnit4::class)
//class HomeScreenScreenshotTest {
//
//    @get:Rule
//    val screenshotRule: ComposeScreenshotTestRule = createComposeScreenshotTestRule()
//
//    @Test
//    fun homeScreen_snapshot_test() {
//        val fakeState = HomeUiState(
//            todoList = listOf(
//                TodoDto("1", "Finish Homework", false, 123L),
//                TodoDto("2", "Read Book", true, 456L)
//            ),
//            showAddDialog = false,
//            newTodoText = ""
//        )
//
//        screenshotRule.setContent {
//            ProFlowAppTheme {
//                HomeScreen(
//                    state = fakeState,
//                    onEvent = {}
//                )
//            }
//        }
//
//        screenshotRule.snap("home_screen_default_state")
//    }
//}