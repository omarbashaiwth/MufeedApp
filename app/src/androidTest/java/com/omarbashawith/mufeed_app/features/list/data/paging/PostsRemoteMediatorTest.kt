package com.omarbashawith.mufeed_app.features.list.data.paging

import androidx.paging.*
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.google.common.truth.Truth.assertThat
import com.omarbashawith.mufeed_app.core.data.model.Post
import com.omarbashawith.mufeed_app.features.list.data.local.PostsDatabase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Test

@OptIn(ExperimentalPagingApi::class,ExperimentalCoroutinesApi::class)
class PostsRemoteMediatorTest(){


    private val fakeDb = Room.inMemoryDatabaseBuilder(ApplicationProvider.getApplicationContext(), PostsDatabase::class.java).build()

    @After
    fun tearDown(){
        fakeDb.clearAllTables()
    }


    @Test
    fun refreshLoadReturnSuccessResultWithValidData() = runTest {
        val remoteMediator = PostsRemoteMediator(FakePostApi(),fakeDb)
        val pagingState = PagingState<Int,Post>(
            listOf(),
            null,
            PagingConfig(15),
            15
        )

        val result = remoteMediator.load(LoadType.REFRESH, pagingState)

        assertThat(result is RemoteMediator.MediatorResult.Success).isTrue()
        assertThat((result as RemoteMediator.MediatorResult.Success).endOfPaginationReached).isFalse()

    }

    @Test
    fun refreshLoadReturnSuccessResultWithEmptyData() = runTest {
        val remoteMediator = PostsRemoteMediator(FakePostApi(true),fakeDb)
        val pagingState = PagingState<Int,Post>(
            listOf(),
            null,
            PagingConfig(15),
            15
        )

        val result = remoteMediator.load(LoadType.REFRESH, pagingState)

        assertThat(result is RemoteMediator.MediatorResult.Success).isTrue()
        assertThat((result as RemoteMediator.MediatorResult.Success).endOfPaginationReached).isTrue()

    }

//    @Test
//    fun refreshLoadReturnErrorResultWithExceptionMessage() = runTest {
//    }

}