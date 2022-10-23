package com.omarbashawith.mufeed_app.features.list.data

import androidx.paging.*
import com.omarbashawith.mufeed_app.features.list.data.local.PostsDatabase
import com.omarbashawith.mufeed_app.features.list.data.paging.PostsRemoteMediator
import com.omarbashawith.mufeed_app.features.list.data.remote.PostApi
import com.omarbashawith.mufeed_app.core.data.model.Post
import com.omarbashawith.mufeed_app.features.list.domain.PostRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import javax.inject.Inject

@ExperimentalPagingApi
class PostRepoImpl @Inject constructor(
    api: PostApi,
    db: PostsDatabase,
): PostRepo {

    private val dao = db.postsDao()

    override val allPosts: Flow<PagingData<Post>> = Pager(
        config = PagingConfig(15),
        remoteMediator = PostsRemoteMediator(api, db),
        pagingSourceFactory = { dao.getAllPosts()}
    ).flow

    override fun postsByQuery(query: String):Flow<PagingData<Post>> = Pager(
        config = PagingConfig(15),
        pagingSourceFactory = {dao.getPostsByQuery(query)}
    ).flow

    override suspend fun updateFavoritePost(id: String, newValue: Boolean) {
        dao.updateFavoritePost(id, newValue)
    }

    override fun getFavoritePosts(): Flow<List<Post>> {
        return dao.getFavoritePosts()
    }
}