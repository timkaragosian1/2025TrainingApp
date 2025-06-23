package com.timkaragosian.proflowapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import com.timkaragosian.proflowapp.presentation.navigation.AppNavHost
import com.timkaragosian.proflowapp.presentation.theme.ProFlowAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            setContent {
                ProFlowAppTheme {
                    val navController = rememberNavController()
                    AppNavHost(navController = navController)
                }
            }
        }
    }
}


//@Preview(showBackground = true, name = "MainActivity preview")
//@Composable
//fun MainActivityPreview() {
//    ProFlowAppTheme {
//        val navController = rememberNavController()
//        AppNavHost(navController = navController)    }
//}