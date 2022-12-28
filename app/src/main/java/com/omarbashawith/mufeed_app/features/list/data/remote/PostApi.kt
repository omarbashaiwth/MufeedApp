package com.omarbashawith.mufeed_app.features.list.data.remote

import com.omarbashawith.mufeed_app.core.data.model.Post
import com.omarbashawith.mufeed_app.features.list.data.FcmToken
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface PostApi {

    @GET("posts/get")
    suspend fun getAllPosts(
        @Query("page") pageNumber: Int,
        @Query("pageSize") pageLimit: Int = 15
    ): List<Post>

    @POST("tokens/save")
    suspend fun sendToken(
        @Body request: FcmToken
    )
}