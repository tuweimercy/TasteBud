package com.example.tastebud.navigation

sealed class Screen(val route: String) {

    // Authentication
    object Splash : Screen("splash_screen")
    object Login : Screen("login_screen")
    object Register : Screen("register")
    object ForgotPassword : Screen("forgot_password")

    // Main Recipe Screens
    object Dashboard : Screen("dashboard")
    object UploadRecipe : Screen("upload_recipe")
    object RecipeDetails : Screen("recipe_details")
    object MyRecipes : Screen("my_recipes")
    object EditRecipe : Screen("edit_recipe/{recipeId}") {

        fun createRoute(recipeId: String): String {
            return "edit_recipe/$recipeId"
        }

    }

    object Profile : Screen("profile")

}