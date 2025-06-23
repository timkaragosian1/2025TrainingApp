package com.timkaragosian.proflowapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import com.timkaragosian.proflowapp.di.networkModule
import com.timkaragosian.proflowapp.di.roomModule
import com.timkaragosian.proflowapp.presentation.navigation.AppNavHost
import com.timkaragosian.proflowapp.presentation.theme.ProFlowAppTheme
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        startKoin{
            androidContext(this@MainActivity)
            modules(networkModule, roomModule)
        }

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