package com.omarbashawith.mufeed_app.features.categories.data

data class Category(
    val label: String,
    val tag: String,
    val icon: Int,
    val isSelected: Boolean = false
)
