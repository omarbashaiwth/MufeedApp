package com.omarbashawith.mufeed_app.features.list.presentation

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
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
import coil.annotation.ExperimentalCoilApi
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.omarbashawith.mufeed_app.R
import com.omarbashawith.mufeed_app.core.presentation.composables.PostItem
import com.omarbashawith.mufeed_app.core.presentation.composables.DefaultTopBar
import com.omarbashawith.mufeed_app.core.presentation.composables.SearchBar
import com.omarbashawith.mufeed_app.core.presentation.composables.SearchBarState
import com.omarbashawith.mufeed_app.features.destinations.PostDetailsScreenDestination
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

@ExperimentalCoilApi
@Destination(start = true)
@Composable
fun ListScreen(
    navigator: DestinationsNavigator,
    viewModel: ListScreenViewModel = hiltViewModel(),
    scaffoldState: ScaffoldState = rememberScaffoldState()
) {
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    val connectivityState by viewModel.connectivityState.collectAsState()
    val searchBarState by viewModel.searchBarState.collectAsState()
    val allPosts = viewModel.allPosts.collectAsLazyPagingItems()
    val postsByQuery = viewModel.postsByQuery.collectAsLazyPagingItems()
    val searchQuery by viewModel.searchQuery.collectAsState()
    val focusRequester = remember { FocusRequester() }
    val scrollState = rememberLazyListState()
    val swipeRefreshState = rememberSwipeRefreshState(
        isRefreshing = allPosts.loadState.refresh is LoadState.Loading
    )

    LaunchedEffect(key1 = allPosts.loadState.refresh) {
        when (val loadState = allPosts.loadState.refresh) {
            is LoadState.Error -> {
                val errorMessage = when (loadState.error) {
                    is IOException -> context.getString(
                        if (connectivityState == ConnectivityState.AVAILABLE) R.string.server_exception_msg
                        else R.string.no_internet_exception_msg
                    )
                    is HttpException -> context.getString(R.string.server_exception_msg)
                    else -> context.getString(R.string.general_exception_msg)
                }
                Log.d("ListScreen","LoadState: $loadState\n Message: $errorMessage\n PostsLoadState: ${allPosts.loadState}")
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
                            viewModel.onSearchBarStateChange(SearchBarState.OPEN)
                        }
                    )
                }
                SearchBarState.OPEN -> {
                    SearchBar(
                        query = searchQuery,
                        onQueryChange = {
                            viewModel.onSearchQueryChange(it)
                        },
                        onCloseClick = {
                            viewModel.onSearchBarStateChange(SearchBarState.CLOSE)
                        },
                        onSearchClick = {

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
                .padding(paddingValues.calculateBottomPadding()),
            state = swipeRefreshState,
            onRefresh = {
                allPosts.refresh()
                viewModel.onInternetStateChanged(context)
                scope.launch {
                    scrollState.animateScrollToItem(0)
                }
            }
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
                            modifier = Modifier
                                .clickable {
                                    navigator.navigate(
                                        PostDetailsScreenDestination(post = it)
                                    )
                                }
                                .padding(bottom = 16.dp),
                            post = it,
                            onFavoriteClick = {
                                viewModel.onToggleFavorite(
                                    id = it.id,
                                    favorite = it.isFavorite
                                )
                            }
                        )
                    }
                }

                if (allPosts.itemSnapshotList.isEmpty() && allPosts.loadState.refresh is LoadState.NotLoading) {
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