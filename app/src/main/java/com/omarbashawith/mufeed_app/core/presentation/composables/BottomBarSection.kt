package com.omarbashawith.mufeed_app.core.presentation.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomAppBar
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavOptions
import androidx.navigation.NavOptionsBuilder
import com.omarbashawith.mufeed_app.R
import com.omarbashawith.mufeed_app.core.presentation.ui.theme.White
import com.omarbashawith.mufeed_app.core.util.BottomNavDestinations
import com.omarbashawith.mufeed_app.features.NavGraphs
import com.omarbashawith.mufeed_app.features.appCurrentDestinationAsState
import com.omarbashawith.mufeed_app.features.appDestination
import com.omarbashawith.mufeed_app.features.startAppDestination
import com.ramcosta.composedestinations.navigation.navigate

@Composable
fun BottomBarSection(
    navController: NavController
) {
    val currentDestination by navController.appCurrentDestinationAsState()

    BottomAppBar(
        modifier = Modifier
            .clip(
                RoundedCornerShape(
                    topStart = 20.dp,
                    topEnd = 20.dp
                )
            ),
        backgroundColor = MaterialTheme.colors.surface
    ) {

        BottomNavDestinations.values().forEach { destination ->
            val isSelected = currentDestination == destination.direction
            BottomNavigationItem(
                selected = isSelected,
                onClick = {
                    navController.navigate(destination.direction) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                icon = {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .clip(MaterialTheme.shapes.small)
                            .background(if (isSelected) MaterialTheme.colors.secondary else Color.Transparent)
                            .padding(5.dp)
                    ) {
                        Icon(
                            imageVector = destination.icon,
                            contentDescription = destination.title,
                            tint = if (isSelected) White else MaterialTheme.colors.onSecondary
                        )
                    }
                }
            )
        }
    }
}