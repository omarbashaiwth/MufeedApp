package com.omarbashawith.mufeed_app.features.list.data.local

import androidx.paging.PagingSource
import androidx.room.*
import com.omarbashawith.mufeed_app.core.data.model.Post
import kotlinx.coroutines.flow.Flow

@Dao
interface PostsDao {

    @Query("SELECT * FROM posts_table WHERE title OR shortDescription LIKE '%' || :query || '%'")
    fun getPostsByQuery(query: String): PagingSource<Int, Post>

    @Query("SELECT * FROM posts_table")
    fun getAllPosts(): PagingSource<Int, Post>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPosts(posts: List<Post>)

    @Query("DELETE FROM posts_table")
    suspend fun deleteAllPosts()

    @Query("UPDATE posts_table SET isFavorite = :newValue WHERE id LIKE :id")
    suspend fun updateFavoritePost(id: String, newValue: Boolean)

    @Query("SELECT * FROM posts_table WHERE isFavorite LIKE 1")
    fun getFavoritePosts(): Flow<List<Post>>

}