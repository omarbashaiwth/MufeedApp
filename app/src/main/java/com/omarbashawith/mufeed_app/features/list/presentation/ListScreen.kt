package com.omarbashawith.mufeed_app.features.list.presentation

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
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
import com.omarbashawith.mufeed_app.core.presentation.composables.DefaultTopBar
import com.omarbashawith.mufeed_app.core.presentation.composables.SearchBar
import com.omarbashawith.mufeed_app.core.presentation.composables.SearchBarState
import com.ramcosta.composedestinations.annotation.Destination
import retrofit2.HttpException
import java.io.IOException

@Destination(start = true)
@Composable
fun ListScreen(
    listScreenViewModel: ListScreenViewModel = hiltViewModel(),
    scaffoldState: ScaffoldState = rememberScaffoldState()
) {
    val context = LocalContext.current
    val searchBarState by listScreenViewModel.searchBarState.collectAsState()
    val allPosts = listScreenViewModel.allPosts.collectAsLazyPagingItems()
    val postsByQuery = listScreenViewModel.postsByQuery.collectAsLazyPagingItems()
    val searchQuery by listScreenViewModel.searchQuery.collectAsState()
    val focusRequester = remember { FocusRequester() }
    val swipeRefreshState = rememberSwipeRefreshState(
        isRefreshing = allPosts.loadState.refresh is LoadState.Loading
    )

    LaunchedEffect(key1 = allPosts.loadState) {
        when (val loadState = allPosts.loadState.refresh) {
            is LoadState.Error -> {
                val errorMessage = when (loadState.error) {
                    is IOException -> context.getString(R.string.Io_exception_msg)
                    is HttpException -> context.getString(R.string.http_exception_msg)
                    else -> context.getString(R.string.general_exception_msg)
                }
                scaffoldState.snackbarHostState.showSnackbar(message = errorMessage)
            }
            else -> Unit
        }
    }


    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            when (searchBarState) {
                SearchBarState.CLOSE -> {
                    DefaultTopBar(
                        title = stringResource(R.string.all_posts),
                        icon = Icons.Default.Search,
                        onIconClick = {
                            listScreenViewModel.onSearchBarStateChange(SearchBarState.OPEN)
                        }
                    )
                }
                SearchBarState.OPEN -> {
                    SearchBar(
                        query = searchQuery,
                        onQueryChange = {
                            listScreenViewModel.onSearchQueryChange(it)
                        },
                        onCloseClick = {
                            listScreenViewModel.onSearchBarStateChange(SearchBarState.CLOSE)
                        },
                        onSearchClick ={

                        },
                        focusRequester = focusRequester
                    )
                    LaunchedEffect(key1 = Unit) {
                        focusRequester.requestFocus()
                    }
                }
            }
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
                items(
                    items = if (searchBarState == SearchBarState.OPEN) postsByQuery else allPosts,
                    key = { it.id }
                ) { post ->
                    post?.let {
                        PostItem(
                            modifier = Modifier.padding(bottom = 16.dp),
                            post = it,
                            showTags = true
                        )
                    }
                }

                if (allPosts.itemSnapshotList.items.isEmpty() && allPosts.loadState.refresh is LoadState.NotLoading) {
                    item {
                        Column(
                            modifier = Modifier.fillParentMaxSize(),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Image(
                                modifier = Modifier.size(250.dp),
                                painter = painterResource(id = R.drawable.error),
                                contentDescription = null,
                            )
                            TextButton(
                                onClick = { allPosts.retry() },
                                border = BorderStroke(1.dp, MaterialTheme.colors.primary)
                            ) {
                                Text(
                                    text = stringResource(R.string.retry),
                                    style = MaterialTheme.typography.body1,
                                    color = MaterialTheme.colors.onSurface
                                )
                            }
                        }
                    }
                }

            }
        }
    }

}