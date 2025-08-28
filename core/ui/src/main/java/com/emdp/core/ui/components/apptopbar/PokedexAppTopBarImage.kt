package com.emdp.core.ui.components.apptopbar

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PokedexAppTopBarImage(
    @DrawableRes backgroundRes: Int,
    height: Dp = 64.dp,
    backgroundColor: Color = MaterialTheme.colorScheme.surface,
    contentScale: ContentScale = ContentScale.FillWidth
) {
    Box(
        modifier = Modifier
            .background(backgroundColor)
            .fillMaxWidth()
            .height(height)
            .windowInsetsPadding(TopAppBarDefaults.windowInsets),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(backgroundRes),
            contentDescription = null,
            modifier = Modifier.matchParentSize(),
            contentScale = contentScale
        )
    }
}