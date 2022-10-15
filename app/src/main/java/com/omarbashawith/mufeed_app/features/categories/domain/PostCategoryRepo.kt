package com.omarbashawith.mufeed_app.features.categories.domain

import androidx.paging.PagingData
import com.omarbashawith.mufeed_app.core.data.model.Post
import kotlinx.coroutines.flow.Flow

interface PostCategoryRepo {

    fun getPostsByTag(): Flow<PagingData<Post>>
}