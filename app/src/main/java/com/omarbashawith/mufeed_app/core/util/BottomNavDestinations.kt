package com.omarbashawith.mufeed_app.core.util

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Star
import androidx.compose.ui.graphics.vector.ImageVector
import com.omarbashawith.mufeed_app.features.destinations.CategoriesScreenDestination
import com.omarbashawith.mufeed_app.features.destinations.FavoritesScreenDestination
import com.omarbashawith.mufeed_app.features.destinations.ListScreenDestination
import com.ramcosta.composedestinations.spec.DirectionDestinationSpec

enum class BottomNavDestinations(
    val direction: DirectionDestinationSpec,
    val title: String,
    val icon: ImageVector
){
    HomeScreen(
        direction = ListScreenDestination,
        title = "الرئيسية",
        icon = Icons.Filled.Home
    ),
    CategoryScreen(
        direction = CategoriesScreenDestination,
        title = "التصنيفات",
        icon = Icons.Filled.Category
    ),
    FavoriteScreen(
        direction = FavoritesScreenDestination,
        title = "المفضلة",
        icon = Icons.Filled.Star
    )
}
