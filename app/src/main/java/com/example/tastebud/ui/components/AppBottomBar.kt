package com.example.tastebud.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.MenuBook
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Dashboard
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.example.tastebud.navigation.Screen

@Composable
fun AppBottomBar(
    currentRoute: String?,
    onDashboardClick: () -> Unit,
    onMyRecipesClick: () -> Unit,
    onUploadClick: () -> Unit,
    onProfileClick: () -> Unit
) {

    val Pink = Color(0xFFE91E63)

    NavigationBar(
        containerColor = Pink
    ) {

        val itemColors = NavigationBarItemDefaults.colors(
            selectedIconColor = Pink,
            selectedTextColor = Color.White,
            unselectedIconColor = Color.LightGray,
            unselectedTextColor = Color.LightGray,
            indicatorColor = Color.White
        )

        NavigationBarItem(
            selected = currentRoute == Screen.Dashboard.route,
            onClick = onDashboardClick,
            icon = {
                Icon(
                    Icons.Default.Dashboard,
                    contentDescription = "Explore"
                )
            },
            label = {
                Text("Explore")
            },
            colors = itemColors
        )

        NavigationBarItem(
            selected = currentRoute == Screen.MyRecipes.route,
            onClick = onMyRecipesClick,
            icon = {
                Icon(
                    Icons.AutoMirrored.Filled.MenuBook,
                    contentDescription = "My Recipes"
                )
            },
            label = {
                Text("My Recipes")
            },
            colors = itemColors
        )

        NavigationBarItem(
            selected = currentRoute == Screen.UploadRecipe.route,
            onClick = onUploadClick,
            icon = {
                Icon(
                    Icons.Default.AddCircle,
                    contentDescription = "Upload"
                )
            },
            label = {
                Text("Upload")
            },
            colors = itemColors
        )

        NavigationBarItem(
            selected = currentRoute == Screen.Profile.route,
            onClick = onProfileClick,
            icon = {
                Icon(
                    Icons.Default.Person,
                    contentDescription = "Profile"
                )
            },
            label = {
                Text("Profile")
            },
            colors = itemColors
        )
    }
}