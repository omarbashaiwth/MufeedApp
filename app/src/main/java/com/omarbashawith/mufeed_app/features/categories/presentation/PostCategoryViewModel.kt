package com.omarbashawith.mufeed_app.features.categories.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.filter
import androidx.paging.map
import com.omarbashawith.mufeed_app.core.data.model.Post
import com.omarbashawith.mufeed_app.features.categories.domain.FilterPostsUseCase
import com.omarbashawith.mufeed_app.features.categories.domain.PostCategoryRepo
import com.omarbashawith.mufeed_app.features.list.domain.PostRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostCategoryViewModel @Inject constructor(
    private val filterPostsUseCase: FilterPostsUseCase,
    private val postRepo: PostRepo
) : ViewModel() {

    var tag = MutableStateFlow("أندرويد")
        private set

    var filterResult = MutableStateFlow<PagingData<Post>>(PagingData.empty())
        private set

    init {
        onTagChange(tag.value)
    }

    fun onTagChange(newTag: String) = viewModelScope.launch {
        tag.value = newTag
        filterResult.value = filterPostsUseCase(newTag,viewModelScope)
        Log.d("TagChange","tag change to $newTag")
    }

    fun onToggleFavorite(id: String, favorite: Boolean) = viewModelScope.launch {
        postRepo.updateFavoritePost(id,!favorite)
    }

}
