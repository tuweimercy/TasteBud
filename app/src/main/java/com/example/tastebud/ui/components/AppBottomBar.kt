package com.example.tastebud.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.MenuBook
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Dashboard
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.example.tastebud.navigation.Screen

@Composable
fun AppBottomBar(
    currentRoute: String?,
    onDashboardClick: () -> Unit,
    onMyRecipesClick: () -> Unit,
    onUploadClick: () -> Unit,
    onProfileClick: () -> Unit,
) {
    NavigationBar(
        containerColor = Color(0xFFE91E63) // TasteBud Pink
    ) {
        val itemColors = NavigationBarItemDefaults.colors(
            selectedIconColor = Color(0xFFE91E63),
            selectedTextColor = Color.White,
            unselectedIconColor = Color.LightGray.copy(alpha = 0.7f),
            unselectedTextColor = Color.LightGray,
            indicatorColor = Color.White
        )

        // Dashboard (Explore)
        NavigationBarItem(
            selected = currentRoute == Screen.Dashboard.route,
            onClick = onDashboardClick,
            colors = itemColors,
            icon = { Icon(Icons.Default.Dashboard, contentDescription = "Explore") },
            label = { Text("Explore") }
        )

        // My Recipes
        NavigationBarItem(
            selected = currentRoute == Screen.Favorites.route,
            onClick = onMyRecipesClick,
            colors = itemColors,
            icon = { Icon(Icons.AutoMirrored.Filled.MenuBook, contentDescription = "My Recipes") },
            label = { Text("My Recipes") }
        )

        // Upload
        NavigationBarItem(
            selected = currentRoute == Screen.UploadRecipe.route,
            onClick = onUploadClick,
            colors = itemColors,
            icon = { Icon(Icons.Default.AddCircle, contentDescription = "Upload") },
            label = { Text("Upload") }
        )

        // Profile
        NavigationBarItem(
            selected = currentRoute == Screen.Profile.route,
            onClick = onProfileClick,
            colors = itemColors,
            icon = { Icon(Icons.Default.Person, contentDescription = "Profile") },
            label = { Text("Profile") }
        )
    }
}
