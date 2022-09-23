package com.omarbashawith.mufeed_app.features.list.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.omarbashawith.mufeed_app.core.data.PostConverter
import com.omarbashawith.mufeed_app.core.data.model.Post
import com.omarbashawith.mufeed_app.features.list.data.paging.RemoteKeys

@Database(entities = [Post::class, RemoteKeys::class ], version = 1, exportSchema = false)
@TypeConverters(PostConverter::class)
abstract class PostsDatabase : RoomDatabase(){

    abstract fun postsDao(): PostsDao
    abstract fun remoteKeysDao(): RemoteKeysDao
}