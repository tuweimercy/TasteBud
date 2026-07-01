package com.example.tastebud.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.tastebud.ui.screens.*
import com.example.tastebud.ui.screens.MyRecipesScreen

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
        composable(Screen.MyRecipes.route) {
            MyRecipesScreen(navController)
        }
        composable(
            route = Screen.EditRecipe.route
        ) { backStackEntry ->

            val recipeId =
                backStackEntry.arguments?.getString("recipeId") ?: ""

            EditRecipeScreen(
                navController = navController,
                recipeId = recipeId
            )

        }
//
//        composable(Screen.RecipeDetails.route) {
//            RecipeDetailsScreen(navController)
//        }

//        composable(Screen.Favorites.route) {
//            FavoritesScreen(navController)
//        }

        composable(Screen.Profile.route) {
            ProfileScreen(navController)}


    }
}