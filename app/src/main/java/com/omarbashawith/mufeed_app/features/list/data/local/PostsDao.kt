package com.omarbashawith.mufeed_app.features.list.data.local

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.omarbashawith.mufeed_app.core.data.model.Post

@Dao
interface PostsDao {

    @Query("SELECT * FROM posts_table WHERE title LIKE '%' || :query || '%'")
    fun getAllPosts(query: String): PagingSource<Int, Post>

    @Query("SELECT * FROM posts_table")
    fun getPostsByTags(): PagingSource<Int, Post>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPosts(posts: List<Post>)

    @Query("DELETE FROM posts_table")
    suspend fun deleteAllPosts()

}