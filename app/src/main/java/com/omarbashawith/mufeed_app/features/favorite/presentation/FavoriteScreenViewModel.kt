package com.omarbashawith.mufeed_app.features.favorite.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.omarbashawith.mufeed_app.features.list.domain.PostRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteScreenViewModel @Inject constructor(
    private val postRepo: PostRepo
) : ViewModel() {

    val favoritePosts = postRepo.getFavoritePosts()

    fun onToggleFavorite(id: String, favorite: Boolean) = viewModelScope.launch {
        postRepo.updateFavoritePost(id, !favorite)
    }
}