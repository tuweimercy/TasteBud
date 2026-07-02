
package com.example.tastebud.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.LockReset
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.tastebud.navigation.Screen
import com.example.tastebud.viewmodel.AuthViewModel
import com.example.tastebud.viewmodel.RecipeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    navController: NavController,
    authViewModel: AuthViewModel = viewModel(),
    recipeViewModel: RecipeViewModel = viewModel()
) {

    val Pink = Color(0xFFE91E63)
    val LightPink = Color(0xFFFFF5F8)
    val SoftPink = Color(0xFFFFC1E3)

    val profile by authViewModel.currentProfile.collectAsState()
    val myRecipes by recipeViewModel.myRecipes.collectAsState()

    val publicRecipes = myRecipes.count { it.isPublic }
    val privateRecipes = myRecipes.count { !it.isPublic }

    LaunchedEffect(Unit) {
        recipeViewModel.loadMyRecipes()
    }

    Scaffold(
        containerColor = LightPink,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "My Profile",
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Pink
                )
            )
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(modifier = Modifier.height(40.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(25.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                )
            ) {

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Box(
                        modifier = Modifier
                            .size(120.dp)
                            .clip(CircleShape)
                            .background(Pink),
                        contentAlignment = Alignment.Center
                    ) {

                        Icon(
                            Icons.Default.Person,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(70.dp)
                        )

                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = profile?.fullName ?: "Guest",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold,
                        color = Pink
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = profile?.email ?: "",
                        color = Color.Gray
                    )

                }

            }

            Spacer(modifier = Modifier.height(35.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(25.dp),
                colors = CardDefaults.cardColors(
                    containerColor = SoftPink
                )
            ) {

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Text(
                        text = "My Recipe Statistics",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = Pink
                    )

                    Spacer(modifier = Modifier.height(25.dp))

                    Text(
                        "🍽 Total Recipes : ${myRecipes.size}",
                        style = MaterialTheme.typography.titleMedium
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        "🌍 Public Recipes : $publicRecipes",
                        style = MaterialTheme.typography.titleMedium
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        "🔒 Private Recipes : $privateRecipes",
                        style = MaterialTheme.typography.titleMedium
                    )

                }

            }

            Spacer(modifier = Modifier.height(35.dp))
            Button(
                onClick = {
                    navController.navigate(Screen.ForgotPassword.route)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(55.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFD81B60)
                )
            ) {

                Icon(
                    Icons.Default.LockReset,
                    contentDescription = null
                )

                Spacer(modifier = Modifier.width(8.dp))

                Text("Reset Password")

            }



            Spacer(modifier = Modifier.height(15.dp))

            Button(
                onClick = {

                    authViewModel.logout()

                    navController.navigate(Screen.Login.route) {
                        popUpTo(0)
                    }

                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(55.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Pink
                )
            ) {

                Icon(Icons.Default.ExitToApp, null)

                Spacer(modifier = Modifier.width(8.dp))

                Text("Logout")

            }

        }

    }

}

