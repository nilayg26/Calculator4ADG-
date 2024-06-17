package com.example.calculator4adg

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.calculator4adg.ui.theme.Calculator4ADGTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Calculator4ADGTheme {
                // A surface container using the 'background' color from the theme
                Navigation()

            }
        }
    }
}
@Composable
fun Navigation(){
    val navController= rememberNavController()
    NavHost(navController = navController, startDestination = WelcomePage.route){
        composable(WelcomePage.route){
            WelcomePage(navController)
        }
        composable(Screen1.route){
            Screen1(navController)
        }
        composable(Screen2.route){
            Screen2(navController)
        }
    }
}