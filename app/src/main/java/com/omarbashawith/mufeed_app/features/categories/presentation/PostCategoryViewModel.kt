package com.omarbashawith.mufeed_app.features.categories.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import androidx.paging.map
import com.omarbashawith.mufeed_app.features.categories.domain.PostCategoryRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class PostCategoryViewModel @Inject constructor(
    postCategoryRepo: PostCategoryRepo
): ViewModel() {

    var tag = MutableStateFlow("أندرويد")
        private set


    val postsByTag = postCategoryRepo.getPostsByTag()


    fun onTagChange(newValue: String) {
        tag.value = newValue
    }
}