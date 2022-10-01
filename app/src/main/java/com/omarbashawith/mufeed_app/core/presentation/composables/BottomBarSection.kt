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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import com.omarbashawith.mufeed_app.core.presentation.ui.theme.White
import com.omarbashawith.mufeed_app.core.util.BottomNavDestinations
import com.omarbashawith.mufeed_app.features.appDestination
import com.ramcosta.composedestinations.navigation.navigate

@Composable
fun BottomBarSection(
    destinations: List<BottomNavDestinations>,
    backStackEntry: NavBackStackEntry?,
    navController: NavController
) {
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
        val appDestinations = backStackEntry?.appDestination()
        destinations.forEach {
            val isSelected = appDestinations == it.destination
            BottomNavigationItem(
                selected = isSelected,
                onClick = {
                    navController.popBackStack()
                    navController.navigate(it.destination){
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
                            imageVector = it.icon,
                            contentDescription = it.title,
                            tint = if (isSelected)  White else MaterialTheme.colors.onSecondary
                        )
                    }
                }
            )
        }
    }
}