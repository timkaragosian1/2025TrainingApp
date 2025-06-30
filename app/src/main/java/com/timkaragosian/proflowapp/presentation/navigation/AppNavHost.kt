package com.timkaragosian.proflowapp.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.timkaragosian.proflowapp.R
import com.timkaragosian.proflowapp.data.network.TodoDto
import com.timkaragosian.proflowapp.presentation.flowresult.FlowResultScreen
import com.timkaragosian.proflowapp.presentation.history.HistoryScreen
import com.timkaragosian.proflowapp.presentation.home.HomeScreen
import com.timkaragosian.proflowapp.presentation.home.HomeScreenContainer
import com.timkaragosian.proflowapp.presentation.signin.SignInRoute
import com.timkaragosian.proflowapp.presentation.signin.SignInScreen
import org.koin.androidx.compose.get

@Composable
fun AppNavHost(navController: NavHostController) {
    val homeScreen = stringResource(id = R.string.home_screen_name)
    val signInScreen = stringResource(id = R.string.signin_screen_name)
    val flowResultScreen = stringResource(id = R.string.flowresult_screen_name)
    val historyScreen = stringResource(id = R.string.history_screen_name)

    NavHost(
        navController = navController,
        startDestination = signInScreen
    ) {
        composable(signInScreen) {
            SignInRoute(
                onNavigationHome = {
                    navController.navigate(homeScreen){
                        popUpTo(signInScreen) { inclusive = true }
                    }
                }
            )
        }

        composable(homeScreen) {
            HomeScreenContainer(
                onNavigateToHistory = { navController.navigate("history") },
                onTaskResults = { todo ->
                    val route = "$flowResultScreen/${todo.id}/${todo.todo}/${todo.completed}/${todo.timestamp}"
                    navController.navigate(route)                }
            )
        }

        composable(
            route = "$flowResultScreen/{id}/{todoText}/{completed}/{timestamp}",
            arguments = listOf(
                navArgument("id") { type = NavType.StringType },
                navArgument("todoText") { type = NavType.StringType },
                navArgument("completed") { type = NavType.BoolType },
                navArgument("timestamp") { type = NavType.LongType },
            )
        ) { backStackEntry ->
            val todoTask = TodoDto(
                id = backStackEntry.arguments?.getString("id") ?: "",
                todo = backStackEntry.arguments?.getString("todoText") ?: "",
                completed = backStackEntry.arguments?.getBoolean("completed") ?: false,
                timestamp = backStackEntry.arguments?.getLong("timestamp") ?: 0L,
            )
            FlowResultScreen(
                todoDto = todoTask,
                onNavigateBack = { navController.popBackStack() },
            )
        }

        composable(historyScreen){
            HistoryScreen(
                onNavigateBack = { navController.popBackStack() }
            )
        }
    }
}
