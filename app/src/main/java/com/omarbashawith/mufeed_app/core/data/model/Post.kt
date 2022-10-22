package com.omarbashawith.mufeed_app.core.data.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "posts_table")
data class Post(
    @PrimaryKey(autoGenerate = false)
    val id: String,
    val title: String,
    val shortDescription: String,
    val links: List<String>,
    val body: String,
    val imageUrl:String,
    val date: String,
    val tags: List<String>,
    val isFavorite: Boolean = false
): Parcelable
