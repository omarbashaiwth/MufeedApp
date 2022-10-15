package com.omarbashawith.mufeed_app.features.list.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.omarbashawith.mufeed_app.core.presentation.composables.SearchBarState
import com.omarbashawith.mufeed_app.features.list.domain.PostRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class ListScreenViewModel @Inject constructor(
    postRepo: PostRepo
): ViewModel() {

    var searchBarState = MutableStateFlow(SearchBarState.CLOSE)
        private set

    var searchQuery = MutableStateFlow("")
        private set


    val posts = searchQuery.flatMapLatest {
        postRepo.allPosts(it)
    }.cachedIn(viewModelScope)

    fun onSearchBarStateChange(newValue: SearchBarState) {
        searchBarState.value = newValue
    }

    fun onSearchQueryChange(newValue: String) {
        searchQuery.value = newValue
    }

}