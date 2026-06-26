package com.example.tastebud.viewmodel


sealed class RecipeState {

    object Idle : RecipeState()

    object Loading : RecipeState()

    object Success : RecipeState()

    data class Error(
        val message: String
    ) : RecipeState()

}