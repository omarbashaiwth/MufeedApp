package com.omarbashawith.mufeed_app.features.categories.presentation

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import com.omarbashawith.mufeed_app.R
import com.omarbashawith.mufeed_app.core.presentation.composables.DefaultTopBar
import com.omarbashawith.mufeed_app.core.presentation.composables.PostItem
import com.omarbashawith.mufeed_app.features.categories.data.Category
import com.ramcosta.composedestinations.annotation.Destination

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Destination
@Composable
fun CategoriesScreen(
    viewModel: PostCategoryViewModel = hiltViewModel()
) {

    val selectedTag by viewModel.tag.collectAsState()
    val context = LocalContext.current
    val postsPaging = viewModel.postsByTag.collectAsLazyPagingItems()
    val postsByTag = postsPaging.itemSnapshotList.items.filter {
        it.tags.contains(selectedTag)
    }
    val categoriesList = listOf(
        Category(stringResource(R.string.android_label),context.getString(R.string.android),R.drawable.ic_android),
        Category(stringResource(R.string.iphone_label),context.getString(R.string.iphone),R.drawable.ic_iphone),
        Category(stringResource(R.string.website_label),context.getString(R.string.website),R.drawable.ic_web),
        Category(stringResource(R.string.chrome_label),context.getString(R.string.chrome_extention),R.drawable.ic_chrome),
        Category(stringResource(R.string.pc_label),context.getString(R.string.pc_programs),R.drawable.ic_computer),
    )

    Scaffold(
        topBar = {
            DefaultTopBar(title = stringResource(R.string.categories))
        }
    ) {
        LazyColumn(
            contentPadding = PaddingValues(16.dp)
        ){
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ){
                    categoriesList.forEach{ category ->
                        CategoryItem(
                            item = Category(
                                label = category.label,
                                icon = category.icon,
                                tag = category.tag,
                                isSelected = selectedTag == category.tag
                            ),
                            onTagClick = {
                                viewModel.onTagChange(category.tag)
                            }
                        )
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))

            }
            items(
                items = postsByTag,
                key = {it.id}
            ){ post ->
                    PostItem(
                        modifier = Modifier.padding(bottom = 16.dp),
                        post = post,
                        showTags = false
                    )

            }
        }
    }
}