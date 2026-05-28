package com.example.tastebud.navigation

sealed class Screen(val route: String) {
    object Splash : Screen("splash_screen")
    object Login : Screen("login_screen")
    // Add other screens here as you develop them, e.g.:
    // object Home : Screen("home_screen")
    // object RecipeDetail : Screen("recipe_detail_screen")
}
