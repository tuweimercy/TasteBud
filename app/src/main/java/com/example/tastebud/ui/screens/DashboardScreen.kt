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
import com.example.tastebud.navigation.Screen
import com.example.tastebud.ui.components.AppBottomBar
import com.example.tastebud.viewmodel.AuthViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
    navController: NavController,
    authViewModel: AuthViewModel = viewModel()
) {

    val Pink = Color(0xFFE91E63)
    val LightPink = Color(0xFFFFF5F8)
    val SoftPink = Color(0xFFF8BBD0)

    var search by remember {
        mutableStateOf("")
    }

    val categories = listOf(
        "Breakfast",
        "Lunch",
        "Dinner",
        "Dessert",
        "Snacks",
        "Drinks"
    )

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
                            imageVector = Icons.Default.ExitToApp,
                            contentDescription = "Logout",
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
                    navController.navigate(Screen.Dashboard.route)
                },

                onFavoritesClick = {
                    navController.navigate(Screen.Dashboard.route)
                },

                onUploadClick = {
                    navController.navigate(Screen.UploadRecipe.route)
                },

                onProfileClick = {
                    navController.navigate(Screen.Dashboard.route)
                }

            )

        },





        floatingActionButton = {

            FloatingActionButton(

                onClick = {

                    navController.navigate("upload_recipe")

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
                            imageVector = Icons.Default.Search,
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
                    text = "Recipe Categories",
                    style = MaterialTheme.typography.titleLarge,
                    color = Pink,
                    fontWeight = FontWeight.Bold
                )

            }
            items(categories) { category ->

                Card(

                    modifier = Modifier.fillMaxWidth(),

                    shape = RoundedCornerShape(18.dp),

                    colors = CardDefaults.cardColors(
                        containerColor = SoftPink
                    ),

                    elevation = CardDefaults.cardElevation(
                        defaultElevation = 6.dp
                    )

                ) {

                    Row(

                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),

                        verticalAlignment = Alignment.CenterVertically

                    ) {

                        Box(

                            modifier = Modifier
                                .size(55.dp)
                                .background(
                                    color = Pink,
                                    shape = CircleShape
                                ),

                            contentAlignment = Alignment.Center

                        ) {

                            Icon(

                                imageVector = Icons.Default.Fastfood,

                                contentDescription = null,

                                tint = Color.White

                            )

                        }

                        Spacer(modifier = Modifier.width(16.dp))

                        Column(
                            modifier = Modifier.weight(1f)
                        ) {

                            Text(

                                text = category,

                                style = MaterialTheme.typography.titleMedium,

                                fontWeight = FontWeight.Bold,

                                color = Pink

                            )

                            Spacer(modifier = Modifier.height(4.dp))

                            Text(

                                text = "Explore tasty $category recipes.",

                                style = MaterialTheme.typography.bodyMedium,

                                color = Color.DarkGray

                            )

                        }

                        Button(

                            onClick = {
                                // Navigate to recipes in this category later
                            },

                            colors = ButtonDefaults.buttonColors(
                                containerColor = Pink
                            ),

                            shape = RoundedCornerShape(10.dp)

                        ) {

                            Text(
                                text = "View",
                                color = Color.White
                            )

                        }

                    }

                }

            }

            item {

                Spacer(modifier = Modifier.height(100.dp))

            }

        }

    }

}