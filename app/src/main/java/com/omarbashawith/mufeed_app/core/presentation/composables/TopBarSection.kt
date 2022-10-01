package com.omarbashawith.mufeed_app.core.presentation.composables

import androidx.compose.foundation.background
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector

@Composable
fun TopBarSection(
    title: String,
    icon: ImageVector? = null,
    onIconClick: () -> Unit =  {}
) {
    TopAppBar(
        modifier = Modifier
            .background(MaterialTheme.colors.primary),
        title = {
            Text(
                text = title,
                style = MaterialTheme.typography.h1,
                color = MaterialTheme.colors.onPrimary
            )
        },
        actions = {
            if (icon != null){
                IconButton(onClick = onIconClick) {
                    Icon(
                        imageVector = icon,
                        contentDescription = null,
                        tint = MaterialTheme.colors.onPrimary
                    )
                }
            }
        }
    )
}