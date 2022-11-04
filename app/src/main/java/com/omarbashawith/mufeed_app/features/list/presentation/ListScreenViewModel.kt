package com.omarbashawith.mufeed_app.features.list.presentation

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.omarbashawith.mufeed_app.core.presentation.composables.SearchBarState
import com.omarbashawith.mufeed_app.features.list.domain.PostRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class ListScreenViewModel @Inject constructor(
    private val postRepo: PostRepo,
) : ViewModel() {

    var searchBarState = MutableStateFlow(SearchBarState.CLOSE)
        private set

    var searchQuery = MutableStateFlow("")
        private set

    var connectivityState = MutableStateFlow(ConnectivityState.UNAVAILABLE)
        private set


    val allPosts = postRepo.allPosts.cachedIn(viewModelScope)

    val postsByQuery = searchQuery.flatMapLatest {
        postRepo.postsByQuery(it)
    }.cachedIn(viewModelScope)

    fun onSearchBarStateChange(newValue: SearchBarState) {
        searchBarState.value = newValue
    }

    fun onSearchQueryChange(newValue: String) {
        searchQuery.value = newValue
    }

    fun onToggleFavorite(id: String, favorite: Boolean) = viewModelScope.launch {
        postRepo.updateFavoritePost(id, !favorite)
    }

    fun onInternetStateChanged(context: Context) {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val networkCapabilities =
                connectivityManager.activeNetwork ?: return
            val activityNetwork = connectivityManager.getNetworkCapabilities(networkCapabilities)
                ?:  return
            connectivityState.value = when {
                activityNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> ConnectivityState.AVAILABLE
                activityNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> ConnectivityState.AVAILABLE
                activityNetwork.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> ConnectivityState.AVAILABLE
                else -> ConnectivityState.UNAVAILABLE
            }
        } else {
            val activeNetworkInfo = connectivityManager.activeNetworkInfo
            connectivityState.value = when (activeNetworkInfo?.type) {
                ConnectivityManager.TYPE_WIFI -> ConnectivityState.AVAILABLE
                ConnectivityManager.TYPE_MOBILE -> ConnectivityState.AVAILABLE
                ConnectivityManager.TYPE_ETHERNET -> ConnectivityState.AVAILABLE
                else -> ConnectivityState.UNAVAILABLE
            }
        }

    }
}

enum class ConnectivityState {
    AVAILABLE,
    UNAVAILABLE,
}