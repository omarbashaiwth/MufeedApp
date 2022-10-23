package com.omarbashawith.mufeed_app.features.favorite.presentation

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.annotation.ExperimentalCoilApi
import com.omarbashawith.mufeed_app.R
import com.omarbashawith.mufeed_app.core.presentation.composables.DefaultTopBar
import com.omarbashawith.mufeed_app.core.presentation.composables.PostItem
import com.omarbashawith.mufeed_app.features.destinations.PostDetailsScreenDestination
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@ExperimentalCoilApi
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Destination
@Composable
fun FavoritesScreen(
    navigator: DestinationsNavigator,
    viewModel: FavoriteScreenViewModel = hiltViewModel()
) {

    val favoritePosts by viewModel.favoritePosts.collectAsState(emptyList())

    Scaffold(
        topBar = {
            DefaultTopBar(
                title = stringResource(R.string.favorites)
            )
        }
    ) {
        LazyColumn(
            contentPadding = PaddingValues(10.dp)
        ) {
            items(items = favoritePosts) {
                PostItem(
                    modifier = Modifier
                        .padding(bottom = 16.dp)
                        .clickable {
                            navigator.navigate(
                                PostDetailsScreenDestination(post = it)
                            )
                        },
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
    }
}