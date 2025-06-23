package com.timkaragosian.proflowapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
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

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ProFlowAppTheme {
        Greeting("Android")
    }
}