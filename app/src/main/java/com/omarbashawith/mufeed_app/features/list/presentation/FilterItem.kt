package com.omarbashawith.mufeed_app.features.list.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.omarbashawith.mufeed_app.core.presentation.ui.theme.*
import com.omarbashawith.mufeed_app.features.list.data.Category

@Composable
fun FilterItem(
    item: Category,
    onFilterClick: () -> Unit,
    modifier: Modifier = Modifier
) {

    val backgroundColor = if (item.isSelected) BistreBrown else Bronze.copy(alpha = 0.55f)
    val labelColor = if (isSystemInDarkTheme()) Gainsboro else White
    Text(
        modifier = modifier
            .clip(RoundedCornerShape(56.dp))
            .background(backgroundColor)
            .clickable { onFilterClick() }
            .padding(8.dp),
        text = item.label,
        style = TextStyle(
            color = labelColor ,
            fontFamily = tajawalFont,
            fontSize = 14.sp
        )
    )

//    Column(
//        modifier = modifier
//            .size(height = 90.dp, width = 55.dp)
//            .clip(RoundedCornerShape(30.dp))
//            .background(
//                color = if (item.isSelected) MaterialTheme.colors.secondaryVariant else MaterialTheme.colors.surface,
//            )
//            .clickable { onTagClick() }
//            .padding(horizontal = 8.dp),
//        verticalArrangement = Arrangement.Center,
//        horizontalAlignment = Alignment.CenterHorizontally
//    ) {
//        Box(
//            modifier = Modifier
//                .background(color = LightSilver, shape = CircleShape),
//            contentAlignment = Alignment.Center,
//        ) {
//            Image(
//                painter = painterResource(item.icon),
//                contentDescription = null,
//                modifier = Modifier.size(33.dp)
//            )
//        }
//        Spacer(modifier = Modifier.height(4.dp))
//        Text(
//            text = item.label,
//            style = MaterialTheme.typography.h4,
//            modifier = Modifier
//                .fillMaxWidth(),
//            textAlign = TextAlign.Center
//        )
//    }

}