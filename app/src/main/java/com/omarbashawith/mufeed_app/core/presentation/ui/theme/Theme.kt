package com.omarbashawith.mufeed_app.core.presentation.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable


private val LightColorPalette = lightColors(
    primary = OldGold,
    onPrimary = White,
    secondary = BistreBrown,
    background = BrightGray,
    surface = White,
    onSurface = EerieBlack
)

private val DarkColorPalette = darkColors(
    primary = EerieBlack,
    onPrimary = Gainsboro,
    secondary = Gainsboro,
    background = ChineseBlack,
    surface = EerieBlack,
    onSurface = Gainsboro
)



    /* Other default colors to override
    background = Color.White,
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black,
    */


@Composable
fun MufeedAppTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = typography,
        shapes = shapes,
        content = content
    )
}