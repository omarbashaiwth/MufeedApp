package com.omarbashawith.mufeed_app.di

import android.app.Application
import androidx.paging.ExperimentalPagingApi
import androidx.room.Room
import com.omarbashawith.mufeed_app.features.categories.data.PostCategoryRepoImpl
import com.omarbashawith.mufeed_app.features.categories.domain.PostCategoryRepo
import com.omarbashawith.mufeed_app.features.list.data.PostRepoImpl
import com.omarbashawith.mufeed_app.features.list.data.local.PostsDatabase
import com.omarbashawith.mufeed_app.features.list.data.remote.PostApi
import com.omarbashawith.mufeed_app.features.list.domain.PostRepo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

//    @Provides
//    @Singleton
//    fun provideClient(): OkHttpClient{
//        val cacheControlIntercept = Interceptor { chain ->
//            val originalResponse: Response = chain.proceed(chain.request())
//            originalResponse.newBuilder()
//                .header("Cache-Control", "max-age=3600")
//                .build()
//        }
//        return OkHttpClient.Builder()
//            .addInterceptor(cacheControlIntercept)
//            .build()
//    }

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("http://192.168.100.14:8080/")
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

    @OptIn(ExperimentalPagingApi::class)
    @Provides
    @Singleton
    fun providePostRepo(
        api: PostApi,
        db: PostsDatabase
    ): PostRepo{
        return PostRepoImpl(api, db)
    }

    @Provides
    @Singleton
    fun providePostCategoryRepo(
        db: PostsDatabase
    ): PostCategoryRepo{
        return PostCategoryRepoImpl(db)
    }
}