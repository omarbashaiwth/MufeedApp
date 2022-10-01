package com.omarbashawith.mufeed_app.features.list.presentation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.omarbashawith.mufeed_app.core.presentation.composables.PostItem

@Composable
fun ListScreen(
    listScreenViewModel: ListScreenViewModel = hiltViewModel()
) {
    val allPosts = listScreenViewModel.allPosts.collectAsLazyPagingItems()

    LazyColumn(
        contentPadding = PaddingValues(10.dp)
    ){
        items(
            items = allPosts,
            key = {it.id}
        ) { post ->
            post?.let {
                PostItem(
                    modifier = Modifier.padding(bottom = 16.dp),
                    post = it
                )
            }
        }
    }
}