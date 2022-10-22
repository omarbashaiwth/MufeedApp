package com.omarbashawith.mufeed_app.core.presentation.ui.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.omarbashawith.mufeed_app.R

val interFont = FontFamily(
    Font(R.font.inter_regular,FontWeight.Normal),
    Font(R.font.inter_bold,FontWeight.Bold)
)

val tajawalFont = FontFamily(
    Font(R.font.tajawal_regular, FontWeight.Normal),
    Font(R.font.tajawal_bold, FontWeight.Bold)
)

val typography = Typography(

    //top bar title & post title
    h1 = TextStyle(
        fontFamily = tajawalFont,
        fontSize = 24.sp
    ),

    // short desc and full body and button
    body1 = TextStyle(
        fontFamily = tajawalFont,
        fontSize = 16.sp
    ),

    // time and tags
    h3 = TextStyle(
        fontFamily = tajawalFont,
        fontSize = 13.sp
    ),

    // categories button
    h4 = TextStyle(
        fontFamily = tajawalFont,
        fontSize = 10.sp
    )

)