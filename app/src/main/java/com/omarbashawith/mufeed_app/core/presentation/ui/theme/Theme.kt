package com.omarbashawith.mufeed_app.core.presentation.ui.theme

import android.annotation.SuppressLint
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable


@SuppressLint("ConflictingOnColor")
private val LightColorPalette = lightColors(
    primary = OldGold,
    secondary = OldGold,
    onSecondary = PhilippineGray,
    onPrimary = White,
    primaryVariant = BistreBrown,
    background = BrightGray,
    surface = White,
    onSurface = EerieBlack,
    secondaryVariant = OldGold
)

private val DarkColorPalette = darkColors(
    primary = EerieBlack,
    secondary = BistreBrown,
    onSecondary = Gainsboro,
    onPrimary = Gainsboro,
    primaryVariant = Gainsboro,
    background = ChineseBlack,
    surface = EerieBlack,
    onSurface = Gainsboro,
    secondaryVariant = BistreBrown
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