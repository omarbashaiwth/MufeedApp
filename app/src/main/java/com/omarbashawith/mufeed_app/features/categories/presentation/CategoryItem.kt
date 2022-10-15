package com.omarbashawith.mufeed_app.features.categories.presentation

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.omarbashawith.mufeed_app.R
import com.omarbashawith.mufeed_app.core.presentation.ui.theme.LightSilver
import com.omarbashawith.mufeed_app.core.presentation.ui.theme.MufeedAppTheme
import com.omarbashawith.mufeed_app.core.presentation.ui.theme.OldGold
import com.omarbashawith.mufeed_app.core.presentation.ui.theme.White
import com.omarbashawith.mufeed_app.features.categories.data.Category

@Composable
fun CategoryItem(
    item: Category,
    onTagClick: () -> Unit,
    modifier: Modifier = Modifier
) {

    Column(
        modifier = modifier
            .size(height = 90.dp, width = 55.dp)
            .clip(RoundedCornerShape(30.dp))
            .background(
                color = if (item.isSelected) MaterialTheme.colors.secondaryVariant else MaterialTheme.colors.surface,
            )
            .clickable { onTagClick() }
            .padding(horizontal = 8.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .background(color = LightSilver, shape = CircleShape),
            contentAlignment = Alignment.Center,
        ) {
            Image(
                painter = painterResource(item.icon),
                contentDescription = null,
                modifier = Modifier.size(33.dp)
            )
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = item.label,
            style = MaterialTheme.typography.h4,
            modifier = Modifier
                .fillMaxWidth(),
            textAlign = TextAlign.Center
        )
    }

}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun CategoryItemPreview() {
    MufeedAppTheme {
        CategoryItem(
            item = Category(
                tag = "",
                label="تطبيقات أندرويد",
                icon = R.drawable.ic_computer,
                isSelected = false,
            ),
            onTagClick = {}
        )
    }
}