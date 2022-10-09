package com.omarbashawith.mufeed_app.features.list.presentation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.omarbashawith.mufeed_app.R
import com.omarbashawith.mufeed_app.core.presentation.composables.PostItem
import com.omarbashawith.mufeed_app.core.presentation.composables.TopBarSection
import com.ramcosta.composedestinations.annotation.Destination

@Destination(start = true)
@Composable
fun ListScreen(
    listScreenViewModel: ListScreenViewModel = hiltViewModel(),
    scaffoldState: ScaffoldState = rememberScaffoldState()
) {
    val allPosts = listScreenViewModel.allPosts.collectAsLazyPagingItems()
    val swipeRefreshState = rememberSwipeRefreshState(
        isRefreshing = allPosts.loadState.refresh is LoadState.Loading
    )

    LaunchedEffect(key1 = allPosts.loadState){
        if (allPosts.loadState.refresh is LoadState.Error) {
            scaffoldState.snackbarHostState.showSnackbar(
                message = (allPosts.loadState.refresh as LoadState.Error).error.message ?: "حصل خطأ .. أعد المحاولة"
            )
        }
    }


    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopBarSection(
                title = stringResource(R.string.all_posts),
                icon = Icons.Outlined.Search,
            )
        }
    ) { paddingValues ->
        SwipeRefresh(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            state = swipeRefreshState,
            onRefresh = { allPosts.refresh() }
        ) {

            LazyColumn(
                contentPadding = PaddingValues(10.dp)
            ) {
                if (allPosts.loadState.refresh is LoadState.NotLoading ||
                        allPosts.loadState.refresh is LoadState.Error) {
                    items(
                        items = allPosts,
                        key = {it.id }
                    ) { post ->
                        post?.let {
                            PostItem(
                                modifier = Modifier.padding(bottom = 16.dp),
                                post = it,
                            )
                        }
                    }

                }

            }
        }
    }



}