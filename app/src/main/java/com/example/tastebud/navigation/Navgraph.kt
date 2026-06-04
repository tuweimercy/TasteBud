package com.example.tastebud.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.tastebud.ui.screens.ForgotPasswordScreen
import com.example.tastebud.ui.screens.LoginScreen
import com.example.tastebud.ui.screens.RegisterScreen
import com.example.tastebud.ui.screens.SplashScreen

@Composable
fun TasteBudNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.Splash.route
    ) {
        composable(route = Screen.Splash.route) {
            SplashScreen(navController)
        }
        composable(route = Screen.Login.route) {
            LoginScreen(navController)
        }
        composable(route = Screen.Register.route) {
            RegisterScreen(navController)
        }
        composable(route = Screen.ForgotPassword.route) {
            ForgotPasswordScreen(navController)
        }
    }
}
