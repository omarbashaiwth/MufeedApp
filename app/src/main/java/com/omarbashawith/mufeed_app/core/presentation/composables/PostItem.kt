package com.omarbashawith.mufeed_app.core.presentation.composables

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Schedule
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.omarbashawith.mufeed_app.core.data.model.Post
import com.omarbashawith.mufeed_app.core.presentation.ui.theme.*

@OptIn(ExperimentalCoilApi::class)
@Composable
fun PostItem(
    modifier: Modifier = Modifier,
    post: Post
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
                painter = rememberImagePainter(data = post.imageUrl),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .size(123.dp),
                contentScale = ContentScale.Crop
            )
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp, vertical = 4.dp),
                text = post.title,
                style = MaterialTheme.typography.h2,
                color = MaterialTheme.colors.secondary,
                textAlign = TextAlign.End,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp),
                text = post.shortDescription,
                style = MaterialTheme.typography.body1,
                color = MaterialTheme.colors.onSurface,
                textAlign = TextAlign.End,
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
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        modifier = Modifier.size(24.dp),
                        imageVector = Icons.Outlined.Schedule,
                        contentDescription = null,
                        tint = MaterialTheme.colors.onSurface
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = post.date.toString(),
                        style = MaterialTheme.typography.h3,
                        color = MaterialTheme.colors.onSurface
                    )
                }

                Row {
                    post.tags.forEach { tag ->
                        val backgroundColor = when (tag) {
                            "موقع ويب" -> CoralRed
                            "أندرويد" -> Midnight
                            "أيفون" -> PomonaGreen
                            "برنامج كمبيوتر" -> BluePigment
                            "استضافة كروم" -> BlueSapphire
                            else -> OldGold
                        }
                        TagItem(
                            backgroundColor = backgroundColor,
                            tag = tag
                        )
                    }
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

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun PostItemPreview() {
    MufeedAppTheme {
        PostItem(
            post = Post(
                id = "123",
                title = "Pixabay",
                shortDescription = "حمل مجاناً وبشكل رسمي أجود الصور ومقاطع الفيديو التي تحتاجها كمصمم أو منتج أو صانع محتوى.",
                tags = listOf("موقع ويب", "أندرويد", "أيفون"),
                body = "",
                imageUrl = "",
                links = listOf(),
                date = 12314964165L
            )
        )
    }
}