package com.example.tastebud.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.tastebud.viewmodel.RecipeState
import com.example.tastebud.viewmodel.RecipeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditRecipeScreen(
    navController: NavController,
    recipeId: String,
    recipeViewModel: RecipeViewModel = viewModel()
) {

    val recipes by recipeViewModel.myRecipes.collectAsState()
    val recipeState by recipeViewModel.recipeState.collectAsState()

    LaunchedEffect(Unit) {
        recipeViewModel.loadMyRecipes()
    }

    LaunchedEffect(recipeState) {
        if (recipeState is RecipeState.Success) {
            recipeViewModel.clearState()
            navController.popBackStack()
        }
    }

    val recipe = recipes.find { it.id == recipeId }

    if (recipe == null) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
        return
    }

    var title by remember { mutableStateOf(recipe.title) }
    var ingredients by remember { mutableStateOf(recipe.ingredients) }
    var instructions by remember { mutableStateOf(recipe.instructions) }
    var category by remember { mutableStateOf(recipe.category) }
    var cookingTime by remember { mutableStateOf(recipe.cookingTime.toString()) }
    var isPublic by remember { mutableStateOf(recipe.isPublic) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Edit Recipe")
                }
            )
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {

            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                label = { Text("Recipe Name") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = category,
                onValueChange = { category = it },
                label = { Text("Category") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = cookingTime,
                onValueChange = { cookingTime = it },
                label = { Text("Cooking Time") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = ingredients,
                onValueChange = { ingredients = it },
                label = { Text("Ingredients") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = instructions,
                onValueChange = { instructions = it },
                label = { Text("Instructions") },
                modifier = Modifier.fillMaxWidth(),
                minLines = 5
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {

                Checkbox(
                    checked = isPublic,
                    onCheckedChange = {
                        isPublic = it
                    }
                )

                Text("Public Recipe")

            }

            Spacer(modifier = Modifier.height(20.dp))

            Button(
                onClick = {

                    recipeViewModel.updateRecipe(
                        recipeId = recipe.id,
                        title = title,
                        ingredients = ingredients,
                        instructions = instructions,
                        category = category,
                        cookingTime = cookingTime.toIntOrNull() ?: 0,
                        isPublic = isPublic
                    )

                },
                modifier = Modifier.fillMaxWidth()
            ) {

                Text("Update Recipe")

            }

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedButton(
                onClick = {
                    navController.popBackStack()
                },
                modifier = Modifier.fillMaxWidth()
            ) {

                Text("Cancel")

            }

        }

    }

}