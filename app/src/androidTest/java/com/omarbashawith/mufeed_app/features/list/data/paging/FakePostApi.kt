package com.omarbashawith.mufeed_app.features.list.data.paging

import com.omarbashawith.mufeed_app.core.data.model.Post
import com.omarbashawith.mufeed_app.core.util.FakeContents
import com.omarbashawith.mufeed_app.features.list.data.remote.PostApi
import kotlinx.coroutines.delay

class FakePostApi(
    private val requiredEmpty: Boolean = false,
) : PostApi {
    override suspend fun getAllPosts(pageNumber: Int, pageLimit: Int): List<Post> {
        delay(1000)

        return if (requiredEmpty) emptyList() else FakeContents.generateFakePosts()
    }

}