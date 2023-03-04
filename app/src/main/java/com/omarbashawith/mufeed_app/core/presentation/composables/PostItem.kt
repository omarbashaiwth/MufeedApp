package com.omarbashawith.mufeed_app.core.presentation.composables

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Schedule
import androidx.compose.material.icons.outlined.StarBorder
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi
import coil.compose.AsyncImage
import coil.compose.rememberImagePainter
import coil.request.ImageRequest
import com.omarbashawith.mufeed_app.R
import com.omarbashawith.mufeed_app.core.data.model.Post
import com.omarbashawith.mufeed_app.core.presentation.ui.theme.*
import com.omarbashawith.mufeed_app.core.util.timeAgo
import com.omarbashawith.mufeed_app.features.list.presentation.ListScreenViewModel

@OptIn(ExperimentalCoilApi::class)
@Composable
fun PostItem(
    modifier: Modifier = Modifier,
    viewModel: ListScreenViewModel?,
    post: Post,
    showTags: Boolean = true,
    onFavoriteClick: () -> Unit
) {
    val context = LocalContext.current
    var downloadedImage by rememberSaveable { mutableStateOf<Uri?>(null)}

    LaunchedEffect(key1 = Unit){
        if (downloadedImage == null) {
            viewModel?.fetchImageFromFirebase(
                imageUri = post.imageUrl,
                onImageDownloadSucceed = {
                    downloadedImage = it
                }

            )
        }
    }
    Card(
        modifier = modifier,
        backgroundColor = MaterialTheme.colors.surface,
        shape = MaterialTheme.shapes.medium
    ) {
        Column(
            modifier = Modifier.padding(bottom = 12.dp),
            horizontalAlignment = Alignment.End,
        ) {
            AsyncImage(
                modifier = Modifier
                    .fillMaxWidth()
                    .size(123.dp),
                model = ImageRequest.Builder(context)
                    .data(downloadedImage)
                    .error(R.drawable.ic_placeholder)
                    .crossfade(true)
                    .build(),
                contentDescription = null,
                contentScale = ContentScale.Crop
            )
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 8.dp, top = 2.dp, bottom = 2.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = post.title,
                    style = MaterialTheme.typography.h1,
                    color = MaterialTheme.colors.primaryVariant,
                    textAlign = TextAlign.Start,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                IconButton(onClick = {onFavoriteClick()}) {
                    Icon(
                        imageVector = if (post.isFavorite) Icons.Filled.Star else Icons.Outlined.StarBorder,
                        contentDescription = null,
                        modifier = Modifier.size(24.dp),
                        tint = if (post.isFavorite) Color.Yellow else MaterialTheme.colors.onSurface
                    )
                }
            }

            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp),
                text = post.shortDescription,
                style = MaterialTheme.typography.body1,
                color = MaterialTheme.colors.onSurface,
                textAlign = TextAlign.Start,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(32.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (showTags) {
                    Row {
                        post.tags.forEach { tag ->
                            val backgroundColor = when (tag) {
                                stringResource(R.string.website) -> CoralRed
                                stringResource(R.string.android) -> Midnight
                                stringResource(R.string.iphone) -> PomonaGreen
                                stringResource(R.string.pc_programs) -> BluePigment
                                stringResource(R.string.chrome_extention) -> BlueSapphire
                                else -> OldGold
                            }
                            TagItem(
                                backgroundColor = backgroundColor,
                                tag = tag
                            )
                        }
                    }
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = post.date.timeAgo(),
                        style = MaterialTheme.typography.h3,
                        color = MaterialTheme.colors.onSurface
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Icon(
                        modifier = Modifier.size(16.dp),
                        imageVector = Icons.Outlined.Schedule,
                        contentDescription = null,
                        tint = MaterialTheme.colors.onSurface
                    )
                }
            }
        }
    }

}

@Composable
private fun TagItem(
    backgroundColor: Color,
    tag: String
) {
    Text(
        modifier = Modifier
            .padding(horizontal = 4.dp)
            .clip(MaterialTheme.shapes.small)
            .background(backgroundColor)
            .padding(horizontal = 4.dp),
        text = tag,
        style = MaterialTheme.typography.h3,
        color = White
    )
}
