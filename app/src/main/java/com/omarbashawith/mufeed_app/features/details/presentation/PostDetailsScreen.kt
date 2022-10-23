package com.omarbashawith.mufeed_app.features.details.presentation

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarBorder
import androidx.compose.material.icons.outlined.StarBorder
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.omarbashawith.mufeed_app.R
import com.omarbashawith.mufeed_app.core.data.model.Post
import com.omarbashawith.mufeed_app.core.presentation.ui.theme.BistreBrown
import com.omarbashawith.mufeed_app.core.presentation.ui.theme.Gainsboro
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@ExperimentalCoilApi
@Destination
@Composable
fun PostDetailsScreen(
    post: Post,
    navigator: DestinationsNavigator,
    viewModel: PostDetailsViewModel = hiltViewModel(),
    hideBottomNav: Boolean = true,
) {

    val context = LocalContext.current
    val uriHandler = LocalUriHandler.current
    val isFavorite by viewModel.isFavorite.collectAsState(initial = post.isFavorite)

    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .padding(10.dp)
            .fillMaxSize()
            .background(MaterialTheme.colors.background),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = post.title,
                style = MaterialTheme.typography.h1,
                color = MaterialTheme.colors.primaryVariant
            )
            CircleIcon(
                icon = Icons.Default.ArrowBack,
                iconSize = 20.dp,
                onClick = { navigator.popBackStack() }
            )
        }
        Spacer(modifier = Modifier.height(24.dp))
        Box(
            modifier = Modifier.fillMaxWidth()
        ) {
            Image(
                painter = rememberImagePainter(data = post.imageUrl) {
                    error(R.drawable.ic_placeholder)
                },
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .padding(bottom = 15.dp)
                    .clip(MaterialTheme.shapes.medium)
                    .fillMaxWidth()
                    .height(200.dp)
            )
            Row(
                modifier = Modifier
                    .padding(start = 16.dp)
                    .align(Alignment.BottomStart)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                CircleIcon(
                    icon = if (isFavorite) Icons.Filled.Star else Icons.Outlined.StarBorder,
                    tint = if (isFavorite) Color.Yellow else MaterialTheme.colors.onPrimary,
                    onClick = {
                       viewModel.onToggleFavorite(
                           id = post.id,
                           favorite = isFavorite
                       )
                    }
                )
                Spacer(modifier = Modifier.width(16.dp))
                CircleIcon(
                    icon = Icons.Default.Share,
                    onClick = {
                        viewModel.onShareClick(
                            context = context,
                            links = post.links,
                            title = post.title,
                            shortDesc = post.shortDescription
                        )
                    }
                )
            }
        }

        Spacer(modifier = Modifier.height(18.dp))
        Text(
            text = post.body,
            style = MaterialTheme.typography.body1,
            color = MaterialTheme.colors.onSurface,
            lineHeight = 24.sp,
            textAlign = TextAlign.Justify
        )
        Spacer(modifier = Modifier.height(18.dp))
        post.links.forEach { link ->
            val label = when {
                link.contains("play.google") -> stringResource(R.string.download_android)
                link.contains("apps.apple") -> stringResource(R.string.download_iphone)
                link.contains("chrome.google") -> stringResource(R.string.download_extension)
                else -> stringResource(R.string.go_to_website)
            }
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Button(
                    modifier = Modifier
                        .fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    onClick = {
                        viewModel.onLinkClick(uriHandler, link)
                    },
                    colors = ButtonDefaults.buttonColors(backgroundColor = BistreBrown)
                ) {
                    Text(
                        text = label,
                        style = MaterialTheme.typography.h3,
                        color = Gainsboro
                    )
                }
            }
        }

    }
}

@Composable
private fun CircleIcon(
    icon: ImageVector,
    onClick: () -> Unit,
    iconSize: Dp = 24.dp,
    tint: Color = MaterialTheme.colors.onPrimary
) {
    Box(
        modifier = Modifier
            .clip(CircleShape)
            .background(MaterialTheme.colors.primary)
            .clickable { onClick() }
            .padding(4.dp),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            modifier = Modifier.size(iconSize),
            tint = tint
        )
    }
}
