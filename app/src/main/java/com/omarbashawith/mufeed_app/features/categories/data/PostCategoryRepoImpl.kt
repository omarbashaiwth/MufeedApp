package com.omarbashawith.mufeed_app.features.categories.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.omarbashawith.mufeed_app.core.data.model.Post
import com.omarbashawith.mufeed_app.features.categories.domain.PostCategoryRepo
import com.omarbashawith.mufeed_app.features.list.data.local.PostsDatabase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PostCategoryRepoImpl @Inject constructor(
    val db: PostsDatabase
): PostCategoryRepo  {

    override fun getPostsByTag(): Flow<PagingData<Post>> {
        return Pager(
            config = PagingConfig(pageSize = 15),
            pagingSourceFactory = {
                db.postsDao().getPostsByTags()
            }
        ).flow
    }
}