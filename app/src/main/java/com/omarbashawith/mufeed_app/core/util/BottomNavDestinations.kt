package com.omarbashawith.mufeed_app.core.util

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.ui.graphics.vector.ImageVector
import com.omarbashawith.mufeed_app.features.destinations.CategoriesScreenDestination
import com.omarbashawith.mufeed_app.features.destinations.DirectionDestination
import com.omarbashawith.mufeed_app.features.destinations.FavoritesScreenDestination
import com.omarbashawith.mufeed_app.features.destinations.ListScreenDestination

sealed class BottomNavDestinations(
    val destination: DirectionDestination,
    val title: String,
    val icon: ImageVector
){
    object HomeScreen: BottomNavDestinations(
        destination = ListScreenDestination,
        title = "الرئيسية",
        icon = Icons.Filled.Home
    )
    object CategoryScreen: BottomNavDestinations(
        destination = CategoriesScreenDestination,
        title = "التصنيفات",
        icon = Icons.Filled.Category
    )
    object FavoriteScreen: BottomNavDestinations(
        destination = FavoritesScreenDestination,
        title = "المفضلة",
        icon = Icons.Filled.Favorite
    )
}
