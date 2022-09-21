package com.omarbashawith.mufeed_app.features.list.data

import androidx.paging.*
import com.omarbashawith.mufeed_app.features.list.data.local.PostsDatabase
import com.omarbashawith.mufeed_app.features.list.data.paging.PostsRemoteMediator
import com.omarbashawith.mufeed_app.features.list.data.remote.PostApi
import com.omarbashawith.mufeed_app.core.data.model.Post
import com.omarbashawith.mufeed_app.features.list.domain.PostRepo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PostRepoImpl @Inject constructor(
    private val api: PostApi,
    private val db: PostsDatabase
): PostRepo {

    @OptIn(ExperimentalPagingApi::class)
    override val allPosts: Flow<PagingData<Post>>
        get() = Pager(
            config = PagingConfig(15),
            remoteMediator = PostsRemoteMediator(api,db)
        ){
            db.postsDao().getAllPosts()
        }.flow
}