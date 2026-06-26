package com.example.tastebud.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Dashboard
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@Composable
fun AppBottomBar(
    currentRoute: String,
    onDashboardClick: () -> Unit,
    onFavoritesClick: () -> Unit,
    onUploadClick: () -> Unit,
    onProfileClick: () -> Unit,
) {

    NavigationBar(
        containerColor = Color(0xFFE91E63) // TasteBud Pink
    ) {

        val itemColors = NavigationBarItemDefaults.colors(
            selectedIconColor = Color(0xFFE91E63),
            selectedTextColor = Color.White,
            unselectedIconColor = Color.LightGray,
            unselectedTextColor = Color.LightGray,
            indicatorColor = Color.White
        )

        // Dashboard
        NavigationBarItem(
            selected = currentRoute == "dashboard",
            onClick = onDashboardClick,
            colors = itemColors,
            icon = {
                Icon(
                    imageVector = Icons.Default.Dashboard,
                    contentDescription = "Dashboard"
                )
            },
            label = {
                Text("Dashboard")
            }
        )

        // Favorites
        NavigationBarItem(
            selected = currentRoute == "favorites",
            onClick = onFavoritesClick,
            colors = itemColors,
            icon = {
                Icon(
                    imageVector = Icons.Default.Favorite,
                    contentDescription = "Favorites"
                )
            },
            label = {
                Text("Favorites")
            }
        )

        // Upload
        NavigationBarItem(
            selected = currentRoute == "upload_recipe",
            onClick = onUploadClick,
            colors = itemColors,
            icon = {
                Icon(
                    imageVector = Icons.Default.AddCircle,
                    contentDescription = "Upload Recipe"
                )
            },
            label = {
                Text("Upload")
            }
        )

        // Profile
        NavigationBarItem(
            selected = currentRoute == "profile",
            onClick = onProfileClick,
            colors = itemColors,
            icon = {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = "Profile"
                )
            },
            label = {
                Text("Profile")
            }
        )
    }
}