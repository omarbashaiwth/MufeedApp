package com.omarbashawith.mufeed_app.core.data.model

import androidx.room.Entity

@Entity(tableName = "posts_table")
data class Post(
    val id: String,
    val title: String,
    val shortDescription: String,
    val links: List<String>,
    val body: String,
    val imageUrl:String,
    val date: Long,
    val tags: List<String>,
    val isFavorite: Boolean = false
)
