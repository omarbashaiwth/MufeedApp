package com.omarbashawith.mufeed_app.features.list.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import androidx.paging.filter
import com.omarbashawith.mufeed_app.core.presentation.composables.SearchBarState
import com.omarbashawith.mufeed_app.features.list.data.PostRepoImpl
import com.omarbashawith.mufeed_app.features.list.domain.PostRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.switchMap
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListScreenViewModel @Inject constructor(
    postRepo: PostRepo
): ViewModel() {

    var searchBarState = MutableStateFlow(SearchBarState.CLOSE)
        private set

    var searchQuery = MutableStateFlow("")
        private set


    @OptIn(ExperimentalCoroutinesApi::class)
    val posts = searchQuery.flatMapLatest {
        postRepo.allPosts(it)
    }.cachedIn(viewModelScope)

    fun onSearchBarStateChange(value: SearchBarState) {
        searchBarState.value = value
    }

    fun onSearchQueryChange(value: String) {
        searchQuery.value = value
    }

}