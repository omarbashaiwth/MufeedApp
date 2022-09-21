package com.omarbashawith.mufeed_app.features.list.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.omarbashawith.mufeed_app.core.data.model.Post

@Database(entities = [Post::class], version = 1, exportSchema = false)
abstract class PostsDatabase : RoomDatabase(){

    abstract fun postsDao(): PostsDao
    abstract fun remoteKeysDao(): RemoteKeysDao
}