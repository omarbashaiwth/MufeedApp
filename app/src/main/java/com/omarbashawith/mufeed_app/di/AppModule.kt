package com.omarbashawith.mufeed_app.di

import android.app.Application
import androidx.room.Room
import androidx.room.RoomDatabase
import com.omarbashawith.mufeed_app.features.list.data.local.PostsDao
import com.omarbashawith.mufeed_app.features.list.data.local.PostsDatabase
import com.omarbashawith.mufeed_app.features.list.data.local.RemoteKeysDao
import com.omarbashawith.mufeed_app.features.list.data.remote.PostApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("http://0.0.0.0:8080/")
            .build()
    }

    @Provides
    @Singleton
    fun providePostApi(retrofit: Retrofit): PostApi {
        return retrofit.create(PostApi::class.java)
    }

    @Provides
    @Singleton
    fun providePostsDatabase(context: Application): PostsDatabase {
        return Room.databaseBuilder(
            context,
            PostsDatabase::class.java,
            "posts.db"
        ).build()
    }
}