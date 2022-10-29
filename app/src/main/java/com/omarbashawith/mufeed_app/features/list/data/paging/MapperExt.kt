package com.omarbashawith.mufeed_app.features.list.data.paging

import com.omarbashawith.mufeed_app.core.data.model.Post
import com.omarbashawith.mufeed_app.features.list.data.local.PostsDao
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.onEach

suspend fun List<Post>.updateFavorites(
    postsDao: PostsDao,
): List<Post>? {
    val favoritePosts = postsDao.getFavoritePosts().first()
    return if(favoritePosts.isNotEmpty()) {
        onEach { remotePost ->
            favoritePosts.onEach { favoritePost ->
                if (remotePost.id == favoritePost.id) {
                    remotePost.isFavorite = favoritePost.isFavorite
                }
            }
        }
    } else null
}