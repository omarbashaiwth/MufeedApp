package com.omarbashawith.mufeed_app.features.list.domain

import androidx.paging.PagingData
import com.omarbashawith.mufeed_app.core.data.model.Post
import kotlinx.coroutines.flow.Flow

interface PostRepo {

    fun allPosts(query: String): Flow<PagingData<Post>>
}