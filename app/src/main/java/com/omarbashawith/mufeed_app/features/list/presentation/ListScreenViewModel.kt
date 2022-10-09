package com.omarbashawith.mufeed_app.features.list.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.omarbashawith.mufeed_app.features.list.domain.PostRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ListScreenViewModel @Inject constructor(
    postRepo: PostRepo
): ViewModel() {

    val allPosts = postRepo.allPosts.cachedIn(viewModelScope)


}