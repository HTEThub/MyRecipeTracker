package com.htet08.myrecipetracker.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun RecipeTopAppBar(
    title: String,
    modifier: Modifier = Modifier,
    leftContent: @Composable (() -> Unit)? = null,
    rightContent: @Composable (() -> Unit)? = null
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(75.dp)
            .background(Color(0xFFF3B245))
            .padding(horizontal = 16.dp),
        contentAlignment = Alignment.Center
    ) {
        // Title in the center
        Text(
            text = title,
            color = Color.White,
            fontSize = 25.sp
        )

        // Left content (e.g., back button)
        Row(
            modifier = Modifier
                .align(Alignment.CenterStart),
            verticalAlignment = Alignment.CenterVertically
        ) {
            leftContent?.invoke()
        }

        // Right content (e.g., next button)
        Row(
            modifier = Modifier
                .align(Alignment.CenterEnd),
            verticalAlignment = Alignment.CenterVertically
        ) {
            rightContent?.invoke()
        }
    }
}

