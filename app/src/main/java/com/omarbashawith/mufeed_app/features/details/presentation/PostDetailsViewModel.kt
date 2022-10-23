package com.omarbashawith.mufeed_app.features.details.presentation

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.platform.UriHandler
import androidx.compose.ui.text.buildAnnotatedString
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.omarbashawith.mufeed_app.features.list.domain.PostRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostDetailsViewModel @Inject constructor(
    private val postRepo: PostRepo
) : ViewModel() {

    var isFavorite = MutableStateFlow(false)
        private set

    fun onLinkClick(uriHandler: UriHandler, link: String) {
        uriHandler.openUri(link)
    }

    fun onShareClick(
        context: Context,
        title: String,
        shortDesc: String,
        links: List<String>
    ) {
        val text = buildAnnotatedString {
            append("${shortDesc}:\n\n")
            links.forEach {link ->
                if (link.contains("play.google")) {
                    append("Google Play:\n")
                }
                if (link.contains("apps.apple")) {
                    append("Play Store:\n")
                }
                append(link)
                append("\n\n")
            }
        }

        val intent = Intent(Intent.ACTION_SEND)
        intent.apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_SUBJECT, title)
            putExtra(Intent.EXTRA_TEXT, text.toString())
        }
        ContextCompat.startActivity(
            context,
            Intent.createChooser(intent, "shareWith"),
            null
        )
    }

    fun onToggleFavorite(id: String, favorite: Boolean) = viewModelScope.launch {
        isFavorite.value = !favorite
        postRepo.updateFavoritePost(id,isFavorite.value)
    }
}