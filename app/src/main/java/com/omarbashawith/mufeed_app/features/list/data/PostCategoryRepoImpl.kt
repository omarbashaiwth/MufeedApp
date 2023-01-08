package com.omarbashawith.mufeed_app.features.list.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.omarbashawith.mufeed_app.core.data.model.Post
import com.omarbashawith.mufeed_app.features.list.domain.PostCategoryRepo
import com.omarbashawith.mufeed_app.features.list.data.local.PostsDatabase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PostCategoryRepoImpl @Inject constructor(
    val db: PostsDatabase
) : PostCategoryRepo {

    override val getPosts: Flow<PagingData<Post>> = Pager(
        config = PagingConfig(pageSize = 15),
        pagingSourceFactory = {
            db.postsDao().getAllPosts()
        }
    ).flow
}
