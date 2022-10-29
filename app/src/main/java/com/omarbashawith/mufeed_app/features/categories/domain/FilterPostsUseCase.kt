package com.omarbashawith.mufeed_app.features.categories.domain

import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.filter
import androidx.paging.flatMap
import com.omarbashawith.mufeed_app.core.data.model.Post
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class FilterPostsUseCase @Inject constructor(
    private val postCategoryRepo: PostCategoryRepo
) {
     operator fun invoke(newTag: String, scope: CoroutineScope): Flow<PagingData<Post>> {
        return postCategoryRepo.getPosts.cachedIn(scope).map{
             it.filter { post ->
                newTag in post.tags
            }
        }
    }
}