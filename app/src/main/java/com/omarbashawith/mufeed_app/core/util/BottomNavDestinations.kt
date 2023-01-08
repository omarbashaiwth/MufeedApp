package com.omarbashawith.mufeed_app.core.util

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Star
import androidx.compose.ui.graphics.vector.ImageVector
import coil.annotation.ExperimentalCoilApi
import com.omarbashawith.mufeed_app.features.destinations.FavoritesScreenDestination
import com.omarbashawith.mufeed_app.features.destinations.ListScreenDestination
import com.ramcosta.composedestinations.spec.DirectionDestinationSpec

enum class BottomNavDestinations(
    val direction: DirectionDestinationSpec,
    val title: String,
    val icon: ImageVector
){
    @ExperimentalCoilApi
    HomeScreen(
        direction = ListScreenDestination,
        title = "الرئيسية",
        icon = Icons.Filled.Home
    ),
    @ExperimentalCoilApi
    FavoriteScreen(
        direction = FavoritesScreenDestination,
        title = "المفضلة",
        icon = Icons.Filled.Star
    )
}
