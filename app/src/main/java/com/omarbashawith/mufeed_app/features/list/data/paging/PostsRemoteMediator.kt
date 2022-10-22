package com.omarbashawith.mufeed_app.features.list.data.paging

import androidx.paging.*
import androidx.room.withTransaction
import com.omarbashawith.mufeed_app.features.list.data.local.PostsDatabase
import com.omarbashawith.mufeed_app.features.list.data.remote.PostApi
import com.omarbashawith.mufeed_app.core.data.model.Post
import retrofit2.HttpException
import java.io.IOException
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class PostsRemoteMediator @Inject constructor(
    private val api: PostApi,
    private val db: PostsDatabase
) : RemoteMediator<Int, Post>() {
    private val postDao = db.postsDao()
    private val remoteKeysDao = db.remoteKeysDao()

//    override suspend fun initialize(): InitializeAction {
//        val cacheTimeout = TimeUnit.MILLISECONDS.convert(1, TimeUnit.DAYS)
//        return if (System.currentTimeMillis() - db.lastUpdated() >= cacheTimeout){
//            InitializeAction.SKIP_INITIAL_REFRESH
//        } else {
//            InitializeAction.LAUNCH_INITIAL_REFRESH
//        }
//    }
    override suspend fun load(loadType: LoadType, state: PagingState<Int, Post>): MediatorResult {
        return try {
            val curPage = when (loadType) {
                LoadType.REFRESH -> {
                    val remoteKeys = getRemoteKeyClosestToCurPosition(state)
                    remoteKeys?.nextPage?.minus(1) ?: 1
                }
                LoadType.PREPEND -> {
                    val remoteKeys = getRemoteKeyForFirstItem(state)
                    val prePage = remoteKeys?.prePage ?: return MediatorResult.Success(
                            endOfPaginationReached = remoteKeys != null
                        )
                    prePage
                }
                LoadType.APPEND -> {
                    val remoteKeys = getRemoteKeyForLastItem(state)
                    val nextPage = remoteKeys?.nextPage ?: return MediatorResult.Success(
                        endOfPaginationReached = remoteKeys != null
                    )
                    nextPage
                }
            }
            val posts = api.getAllPosts(pageNumber = curPage)
            val endOfPagination = posts.isEmpty()

            val prePage = if (curPage == 1) null else curPage - 1
            val nextPage = if (endOfPagination) null else curPage + 1

            db.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    postDao.deleteAllPosts()
                    remoteKeysDao.deleteAllRemoteKeys()
                }
                val keys = posts.map {
                    RemoteKeys(
                        id = it.id,
                        prePage = prePage,
                        nextPage = nextPage
                    )
                }

                postDao.insertPosts(posts)
                remoteKeysDao.insertAllRemoteKeys(keys)
            }

            MediatorResult.Success(
                endOfPaginationReached = endOfPagination
            )
        } catch (e: IOException) {
            MediatorResult.Error(e)
        } catch (e: HttpException) {
            MediatorResult.Error(e)
        }
    }

    private suspend fun getRemoteKeyForLastItem(
        state: PagingState<Int, Post>
    ): RemoteKeys? {
        val lastPage = state.pages.lastOrNull {
            it.data.isNotEmpty()
        }
        val lastItemInLastPage = lastPage?.data?.lastOrNull()
        val remoteKeyForLastItem = lastItemInLastPage?.let { post ->
            remoteKeysDao.getRemoteKeysById(post.id)
        }

        return remoteKeyForLastItem
    }

    private suspend fun getRemoteKeyForFirstItem(
        state: PagingState<Int, Post>
    ): RemoteKeys? {
        val firstPage = state.pages.firstOrNull {
            it.data.isNotEmpty()
        }
        val firstItemInFirstPage = firstPage?.data?.firstOrNull()
        val remoteKeyForFirstItem = firstItemInFirstPage?.let { post ->
            remoteKeysDao.getRemoteKeysById(post.id)
        }

        return remoteKeyForFirstItem
    }

    private suspend fun getRemoteKeyClosestToCurPosition(
        state: PagingState<Int, Post>
    ): RemoteKeys? {
        val curPosition = state.anchorPosition
        val closestItemToCurPosition = curPosition?.let {
            state.closestItemToPosition(it)
        }
        val remoteKeyForItem = closestItemToCurPosition?.let { post ->
            remoteKeysDao.getRemoteKeysById(post.id)
        }

        return remoteKeyForItem
    }

}