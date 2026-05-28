package com.example.tastebud.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
//allows us to define our navigation type
//backstack:previous screen//foreground : screen in view
import androidx.navigation.NavType
//container for all our navigation screens
import androidx.navigation.compose.NavHost
//allows defination of navigation composable functions
import androidx.navigation.compose.composable
//carries path route name to different screens //navigation
import androidx.navigation.navArgument
import com.example.tastebud.ui.screens.*
@Composable
fun TasteBudNavGraph(navController: NavHostController){
    NavHost(
        navController = navController,
        startDestination = Screen.Splash.route
    ) {
        //state our app screens that are def. in screens.kt
        composable(route = Screen.Splash.route) {
            SplashScreen(navController)
        }
        composable(route = Screen.Login.route) {
            LoginScreen(navController)
        }
    }
}