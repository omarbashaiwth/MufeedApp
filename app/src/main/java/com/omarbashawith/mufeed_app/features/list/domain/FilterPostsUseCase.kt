package com.omarbashawith.mufeed_app.features.list.domain

import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.filter
import com.omarbashawith.mufeed_app.core.data.model.Post
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class FilterPostsUseCase @Inject constructor(
    private val postCategoryRepo: PostCategoryRepo
) {
    operator fun invoke(newFilter: String, scope: CoroutineScope): Flow<PagingData<Post>> {
        return postCategoryRepo.getPosts.cachedIn(scope).map { allPosts ->
            if (newFilter == "الكل") allPosts else allPosts.filter { post -> newFilter in post.tags}
        }
    }
}