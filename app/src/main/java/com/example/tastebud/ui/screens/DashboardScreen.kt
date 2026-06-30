package com.example.tastebud.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Fastfood
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.tastebud.navigation.Screen
import com.example.tastebud.ui.components.AppBottomBar
import com.example.tastebud.viewmodel.AuthViewModel
import com.example.tastebud.viewmodel.RecipeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
    navController: NavController,
    authViewModel: AuthViewModel = viewModel(),
    recipeViewModel: RecipeViewModel = viewModel()
) {

    val Pink = Color(0xFFE91E63)
    val LightPink = Color(0xFFFFF5F8)

    var search by remember { mutableStateOf("") }

    var selectedCategory by remember {
        mutableStateOf("All")
    }

    val categories = listOf(
        "All",
        "Breakfast",
        "Lunch",
        "Dinner",
        "Dessert",
        "Snacks",
        "Drinks"
    )

    val recipes by recipeViewModel.publicRecipes.collectAsState()

    LaunchedEffect(Unit) {
        recipeViewModel.loadPublicRecipes()
    }

    val filteredRecipes = recipes.filter {

        (selectedCategory == "All" ||
                it.category == selectedCategory) &&

                it.title.contains(
                    search,
                    ignoreCase = true
                )

    }

    Scaffold(

        containerColor = LightPink,

        topBar = {

            TopAppBar(

                title = {

                    Text(
                        text = "TasteBud",
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )

                },

                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Pink
                ),

                actions = {

                    IconButton(

                        onClick = {

                            authViewModel.logout()

                            navController.navigate(Screen.Login.route) {

                                popUpTo(Screen.Dashboard.route) {
                                    inclusive = true
                                }

                            }

                        }

                    ) {

                        Icon(
                            Icons.Default.ExitToApp,
                            contentDescription = null,
                            tint = Color.White
                        )

                    }

                }

            )

        },
        bottomBar = {

            AppBottomBar(

                currentRoute = Screen.Dashboard.route,

                onDashboardClick = {

                    navController.navigate(Screen.Dashboard.route) {
                        launchSingleTop = true
                    }

                },

                onMyRecipesClick = {

                    navController.navigate(Screen.Favorites.route) {
                        launchSingleTop = true
                    }

                },

                onUploadClick = {

                    navController.navigate(Screen.UploadRecipe.route) {
                        launchSingleTop = true
                    }

                },

                onProfileClick = {

                    navController.navigate(Screen.Profile.route) {
                        launchSingleTop = true
                    }

                }

            )

        },

        floatingActionButton = {

            FloatingActionButton(

                onClick = {

                    navController.navigate(Screen.UploadRecipe.route)

                },

                containerColor = Pink,

                contentColor = Color.White

            ) {

                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Upload Recipe"
                )

            }

        }

    ) { padding ->

        LazyColumn(

            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),

            verticalArrangement = Arrangement.spacedBy(16.dp)

        ) {

            item {

                Text(

                    text = "Welcome to TasteBud 👋",

                    style = MaterialTheme.typography.headlineMedium,

                    color = Pink,

                    fontWeight = FontWeight.Bold

                )

            }

            item {

                OutlinedTextField(

                    value = search,

                    onValueChange = {

                        search = it

                    },

                    modifier = Modifier.fillMaxWidth(),

                    label = {

                        Text("Search recipes")

                    },

                    leadingIcon = {

                        Icon(

                            Icons.Default.Search,

                            contentDescription = null,

                            tint = Pink

                        )

                    },

                    colors = OutlinedTextFieldDefaults.colors(

                        focusedBorderColor = Pink,

                        focusedLabelColor = Pink,

                        cursorColor = Pink

                    ),

                    shape = RoundedCornerShape(15.dp)

                )

            }

            item {

                Text(

                    text = "Categories",

                    style = MaterialTheme.typography.titleLarge,

                    fontWeight = FontWeight.Bold,

                    color = Pink

                )

            }

            items(categories) { category ->

                ElevatedButton(

                    onClick = {

                        selectedCategory = category

                    },

                    colors = ButtonDefaults.buttonColors(

                        containerColor =
                            if (selectedCategory == category)
                                Pink
                            else
                                Color.White

                    ),

                    modifier = Modifier.fillMaxWidth()

                ) {

                    Icon(

                        Icons.Default.Fastfood,

                        contentDescription = null,

                        tint =
                            if (selectedCategory == category)
                                Color.White
                            else
                                Pink

                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    Text(

                        text = category,

                        color =
                            if (selectedCategory == category)
                                Color.White
                            else
                                Pink

                    )

                }

            }

            item {

                Spacer(modifier = Modifier.height(10.dp))

                Text(

                    text = "Uploaded Recipes",

                    style = MaterialTheme.typography.titleLarge,

                    fontWeight = FontWeight.Bold,

                    color = Pink

                )

            }
            items(filteredRecipes) { recipe ->

                Card(

                    modifier = Modifier.fillMaxWidth(),

                    shape = RoundedCornerShape(18.dp),

                    colors = CardDefaults.cardColors(
                        containerColor = Color.White
                    ),

                    elevation = CardDefaults.cardElevation(
                        defaultElevation = 6.dp
                    )

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

                            style = MaterialTheme.typography.bodyMedium,

                            color = Color.Gray

                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        Text(

                            text = "Ingredients",

                            style = MaterialTheme.typography.titleMedium,

                            fontWeight = FontWeight.Bold

                        )

                        Text(recipe.ingredients)

                        Spacer(modifier = Modifier.height(10.dp))

                        Text(

                            text = "Instructions",

                            style = MaterialTheme.typography.titleMedium,

                            fontWeight = FontWeight.Bold

                        )

                        Text(recipe.instructions)

                        Spacer(modifier = Modifier.height(12.dp))

                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {

                            Box(

                                modifier = Modifier
                                    .size(40.dp)
                                    .background(
                                        Pink,
                                        CircleShape
                                    ),

                                contentAlignment = Alignment.Center

                            ) {

                                Text(

                                    text = recipe.ownerName.first()
                                        .uppercase(),

                                    color = Color.White,

                                    fontWeight = FontWeight.Bold

                                )

                            }

                            Spacer(modifier = Modifier.width(10.dp))

                            Column {

                                Text(
                                    text = recipe.ownerName,
                                    fontWeight = FontWeight.Bold
                                )

                                Text(
                                    text = "Recipe Creator",
                                    color = Color.Gray
                                )

                            }

                        }

                    }

                }

            }
            item {

                Spacer(
                    modifier = Modifier.height(100.dp)
                )

            }

        }

    }

}