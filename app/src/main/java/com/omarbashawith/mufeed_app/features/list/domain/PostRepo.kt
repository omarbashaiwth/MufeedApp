package com.omarbashawith.mufeed_app.features.list.domain

import androidx.paging.PagingData
import com.omarbashawith.mufeed_app.core.data.model.Post
import kotlinx.coroutines.flow.Flow

interface PostRepo {


    val allPosts: Flow<PagingData<Post>>

    fun postsByQuery(query: String): Flow<PagingData<Post>>

    suspend fun updateFavoritePost(id: String, newValue: Boolean)

    fun getFavoritePosts(): Flow<List<Post>>
}