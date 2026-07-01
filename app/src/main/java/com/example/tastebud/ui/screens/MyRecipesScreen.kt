package com.example.tastebud.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.tastebud.navigation.Screen
import com.example.tastebud.viewmodel.RecipeViewModel

@Composable
fun MyRecipesScreen(
    navController: NavController,
    recipeViewModel: RecipeViewModel = viewModel()
) {

    val Pink = Color(0xFFE91E63)
    val LightPink = Color(0xFFFFF5F8)

    val recipes by recipeViewModel.myRecipes.collectAsState()

    LaunchedEffect(Unit) {
        recipeViewModel.loadMyRecipes()
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(LightPink)
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        item {

            Spacer(modifier = Modifier.height(30.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Pink
                ),
                elevation = CardDefaults.cardElevation(8.dp)
            ) {

                Text(
                    text = "🍽 My Recipes",
                    modifier = Modifier.padding(20.dp),
                    style = MaterialTheme.typography.headlineMedium,
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )

            }

        }

        if (recipes.isEmpty()) {

            item {

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp)
                ) {

                    Text(
                        text = "You haven't uploaded any recipes yet.",
                        modifier = Modifier.padding(20.dp),
                        style = MaterialTheme.typography.bodyLarge
                    )

                }

            }

        }

        items(recipes) { recipe ->

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                ),
                elevation = CardDefaults.cardElevation(8.dp)
            ) {

                Column(
                    modifier = Modifier.padding(16.dp)
                ) {

                    if (recipe.imageUrl.isNotEmpty()) {

                        AsyncImage(
                            model = recipe.imageUrl,
                            contentDescription = recipe.title,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(200.dp)
                        )

                        Spacer(modifier = Modifier.height(12.dp))
                    }

                    Text(
                        text = recipe.title,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = Pink
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = recipe.category,
                        color = Color.Gray
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = "Ingredients",
                        fontWeight = FontWeight.Bold,
                        color = Pink
                    )

                    Text(recipe.ingredients)

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = "Instructions",
                        fontWeight = FontWeight.Bold,
                        color = Pink
                    )

                    Text(recipe.instructions)

                    Spacer(modifier = Modifier.height(20.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {

                        Button(
                            onClick = {
                                navController.navigate(
                                    Screen.EditRecipe.createRoute(recipe.id)
                                )
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Pink
                            )
                        ) {

                            Icon(
                                Icons.Default.Edit,
                                contentDescription = null
                            )

                            Spacer(modifier = Modifier.width(6.dp))

                            Text("Edit")

                        }

                        Button(
                            onClick = {
                                recipeViewModel.deleteRecipe(recipe)
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.Red
                            )
                        ) {

                            Icon(
                                Icons.Default.Delete,
                                contentDescription = null
                            )

                            Spacer(modifier = Modifier.width(6.dp))

                            Text("Delete")

                        }

                    }

                }

            }

        }

        item {
            Spacer(modifier = Modifier.height(100.dp))
        }

    }

}