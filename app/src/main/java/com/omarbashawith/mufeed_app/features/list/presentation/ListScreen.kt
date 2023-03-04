package com.omarbashawith.mufeed_app.features.list.presentation

import android.annotation.SuppressLint
import android.net.Uri
import android.util.Log
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
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
import com.omarbashawith.mufeed_app.features.list.data.Category
import com.omarbashawith.mufeed_app.features.destinations.PostDetailsScreenDestination
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
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
    val filteredPosts = viewModel.filterResult.collectAsLazyPagingItems()
    val postsByQuery = viewModel.postsByQuery.collectAsLazyPagingItems()
    val searchQuery by viewModel.searchQuery.collectAsState()
    val focusRequester = remember { FocusRequester() }
    val scrollState = rememberLazyListState()
    val swipeRefreshState = rememberSwipeRefreshState(
        isRefreshing = allPosts.loadState.refresh is LoadState.Loading
    )
    val selectedFilter by viewModel.filter.collectAsState()

    val filtersList = listOf(
        Category(
            label = stringResource(R.string.no_filter),
            tag = context.getString(R.string.no_filter)
        ),
        Category(
            label = stringResource(R.string.website_label),
            tag = context.getString(R.string.website),
        ),
        Category(
            label = stringResource(R.string.android_label),
            tag = context.getString(R.string.android),
        ),
        Category(
            label = stringResource(R.string.iphone_label),
            tag = context.getString(R.string.iphone),
        )

    )

    LaunchedEffect(key1 = filteredPosts.loadState.refresh) {
        when (val loadState = filteredPosts.loadState.refresh) {
            is LoadState.Error -> {
                val errorMessage = when (loadState.error) {
                    is IOException -> context.getString(
                        if (connectivityState == ConnectivityState.AVAILABLE) R.string.server_exception_msg
                        else R.string.no_internet_exception_msg
                    )
                    is HttpException -> context.getString(R.string.server_exception_msg)
                    else -> context.getString(R.string.general_exception_msg)
                }
                Log.d(
                    "ListScreen",
                    "LoadState: $loadState\n Message: $errorMessage\n PostsLoadState: ${filteredPosts.loadState}"
                )
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
                        elevation = 0.dp,
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
    ) {
        SwipeRefresh(
            modifier = Modifier
                .fillMaxSize(),
            state = swipeRefreshState,
            onRefresh = {
                allPosts.refresh()
                viewModel.onInternetStateChanged(context)
                scope.launch {
                    scrollState.animateScrollToItem(0)
                }
            }
        ) {
            LazyColumn {
                if (searchBarState == SearchBarState.CLOSE) {
                    // Filter Section
                    item {
                        Surface(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(MaterialTheme.colors.primary)
                                .padding(12.dp),
                        ) {
                            Row(
                                modifier = Modifier
                                    .background(MaterialTheme.colors.primary)
                                    .padding(8.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween

                            ) {
                                filtersList.forEach { category ->
                                    FilterItem(
                                        item = Category(
                                            label = category.label,
                                            tag = category.tag,
                                            isSelected = selectedFilter == category.tag
                                        ),
                                        onFilterClick = {
                                            if (selectedFilter != category.tag) {
                                                viewModel.onFilterChange(category.tag)
                                            }
                                        }
                                    )
                                }
                            }
                        }
                    }
                }
                item {
                    Spacer(modifier = Modifier.height(12.dp))
                }

                items(
                    items = if (searchBarState == SearchBarState.OPEN) postsByQuery else filteredPosts,
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
                                .padding(bottom = 16.dp, start = 10.dp, end = 10.dp),
                            post = it,
                            onFavoriteClick = {
                                viewModel.onToggleFavorite(
                                    id = it.id,
                                    favorite = it.isFavorite
                                )
                            },
                            viewModel = viewModel
                        )
                    }
                }

                if (filteredPosts.itemSnapshotList.isEmpty() && filteredPosts.loadState.refresh is LoadState.NotLoading) {
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
                                onClick = { filteredPosts.retry() },
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