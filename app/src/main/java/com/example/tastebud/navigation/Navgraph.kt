package com.example.tastebud.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.tastebud.ui.screens.*

@Composable
fun TasteBudNavGraph(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Splash.route
    ) {

        // Splash
        composable(Screen.Splash.route) {
            SplashScreen(navController)
        }

        // Authentication
        composable(Screen.Login.route) {
            LoginScreen(navController)
        }

        composable(Screen.Register.route) {
            RegisterScreen(navController)
        }

        composable(Screen.ForgotPassword.route) {
            ForgotPasswordScreen(navController)
        }

        // Main Screens
        composable(Screen.Dashboard.route) {
            DashboardScreen(navController)
        }

        composable(Screen.UploadRecipe.route) {
            UploadRecipeScreen(navController)
        }
//
//        composable(Screen.RecipeDetails.route) {
//            RecipeDetailsScreen(navController)
//        }

//        composable(Screen.Favorites.route) {
//            FavoritesScreen(navController)
//        }

//        composable(Screen.Profile.route) {
//            ProfileScreen(navController)}


    }
}