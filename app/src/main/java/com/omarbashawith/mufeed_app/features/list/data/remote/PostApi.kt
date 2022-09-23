package com.omarbashawith.mufeed_app.features.list.data.remote

import com.omarbashawith.mufeed_app.core.data.model.Post
import retrofit2.http.GET
import retrofit2.http.Query

interface PostApi {

    @GET("/posts/get")
    suspend fun getAllPosts(
        @Query("page") pageNumber: Int,
        @Query("limit") pageLimit: Int = 15
    ): List<Post>
}