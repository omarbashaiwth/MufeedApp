package com.omarbashawith.mufeed_app.core.presentation.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.DefaultTintColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import com.omarbashawith.mufeed_app.R

@Composable
fun DefaultTopBar(
    title: String,
    icon: ImageVector? = null,
    onIconClick: () -> Unit = {}
) {
    TopAppBar(
        modifier = Modifier
            .background(MaterialTheme.colors.primary),
        title = {
            Text(
                text = title,
                style = MaterialTheme.typography.h1,
                color = MaterialTheme.colors.onPrimary,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Start
            )
        },
        actions = {
            if (icon != null) {
                IconButton(onClick = onIconClick) {
                    Icon(
                        imageVector = icon,
                        contentDescription = null,
                        tint = MaterialTheme.colors.onPrimary
                    )
                }
            }
        }
    )
}

@Composable
fun SearchBar(
    query: String,
    onQueryChange: (String) -> Unit,
    onCloseClick: () -> Unit,
    onSearchClick: (String) -> Unit,
    focusRequester: FocusRequester
) {
    TextField(
        modifier = Modifier
            .fillMaxWidth()
            .focusRequester(focusRequester),
        value = query,
        onValueChange = { onQueryChange(it) },
        singleLine = true,
        placeholder = {
            Text(stringResource(R.string.search_here_hint))
        },
        trailingIcon = {
            IconButton(
                onClick = { onSearchClick(query) }
            ) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = null,
                )
            }

        },
        leadingIcon = {
            IconButton(
                onClick = {
                    if (query.isNotBlank()) {
                        onQueryChange("")
                    } else {
                        onCloseClick()
                    }
                }
            ) {
                Icon(
                    imageVector = if (query.isNotBlank()) Icons.Default.Close else Icons.Default.ArrowForward,
                    contentDescription = null,
                )
            }
        },
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Search
        ),
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = MaterialTheme.colors.primary,
            unfocusedIndicatorColor = Color.Transparent,
            cursorColor = LocalContentColor.current.copy(alpha = LocalContentAlpha.current)
        )

    )
}

enum class SearchBarState {
    CLOSE,
    OPEN
}