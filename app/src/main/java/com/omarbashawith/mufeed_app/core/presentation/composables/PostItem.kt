package com.omarbashawith.mufeed_app.core.presentation.composables

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Schedule
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.omarbashawith.mufeed_app.R
import com.omarbashawith.mufeed_app.core.data.model.Post
import com.omarbashawith.mufeed_app.core.presentation.ui.theme.*
import com.omarbashawith.mufeed_app.core.util.timeAgo

@OptIn(ExperimentalCoilApi::class)
@Composable
fun PostItem(
    modifier: Modifier = Modifier,
    post: Post,
) {

    Card(
        modifier = modifier,
        backgroundColor = MaterialTheme.colors.surface,
        shape = MaterialTheme.shapes.medium
    ) {
        Column(
            modifier = Modifier.padding(bottom = 12.dp),
            horizontalAlignment = Alignment.End,
        ) {
            Image(
                painter = rememberImagePainter(data = post.imageUrl){
                     error(R.drawable.ic_placeholder)
                },
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .size(123.dp),
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
                    style = MaterialTheme.typography.h2,
                    color = MaterialTheme.colors.primaryVariant,
                    textAlign = TextAlign.Start,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                IconButton(onClick = {}) {
                    Icon(
                        imageVector = if (post.isFavorite) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                        contentDescription = null,
                        modifier = Modifier.size(24.dp),
                        tint = if (post.isFavorite) Color.Red else MaterialTheme.colors.onSurface
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
                Row {
                    post.tags.forEach { tag ->
                        val backgroundColor = when (tag) {
                            stringResource(R.string.website) -> CoralRed
                            stringResource(R.string.android) -> Midnight
                            stringResource(R.string.ios) -> PomonaGreen
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
