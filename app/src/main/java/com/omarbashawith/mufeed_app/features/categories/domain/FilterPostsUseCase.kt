package com.omarbashawith.mufeed_app.features.categories.domain

import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.filter
import com.omarbashawith.mufeed_app.core.data.model.Post
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class FilterPostsUseCase @Inject constructor(
    private val postCategoryRepo: PostCategoryRepo
) {
    suspend operator fun invoke(newTag: String, scope: CoroutineScope): PagingData<Post> {
        return postCategoryRepo.getPosts.cachedIn(scope).first()
            .filter { post ->
                newTag in post.tags
            }
    }
}