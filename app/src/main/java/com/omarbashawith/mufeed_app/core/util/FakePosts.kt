package com.omarbashawith.mufeed_app.core.util

import com.omarbashawith.mufeed_app.core.data.model.Post

object FakeContents {

    fun generateFakePosts(): List<Post> {
        val posts = (1..50).map {
            Post(
                id = "fake Id $it",
                title = "fake title $it",
                shortDescription = "fake short desc $it",
                links = arrayListOf("fake link 1", "fake link 2"),
                body = "fake body $it",
                imageUrl = "fake image url $it",
                date = 0L,
                tags = arrayListOf("fake tag 1", "fake tag 2")
            )
        }
        return posts
    }
}