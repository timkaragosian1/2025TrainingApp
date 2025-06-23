package com.timkaragosian.proflowapp.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.timkaragosian.proflowapp.R
import com.timkaragosian.proflowapp.presentation.flowresult.FlowResultScreen
import com.timkaragosian.proflowapp.presentation.home.HomeScreen
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
        startDestination = homeScreen
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
            HomeScreen(
                onSubmit = { inputValue ->
                    navController.navigate("$flowResultScreen/$inputValue")
                }
            )
        }
        composable(
            route = "$flowResultScreen/{result}",
            arguments = listOf(navArgument("result") { type = NavType.StringType })
        ) { backStackEntry ->
            val result = backStackEntry.arguments?.getString("result") ?: ""
            FlowResultScreen(result = result)
        }
    }
}
