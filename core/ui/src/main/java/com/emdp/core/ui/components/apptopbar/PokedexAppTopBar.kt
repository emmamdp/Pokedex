package com.emdp.core.ui.components.apptopbar

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.emdp.core.ui.components.favorite.PokedexFavoriteButton

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PokedexAppTopBar(
    titleText: String,
    textColor: Color,
    onBackClick: () -> Unit,
    showFavoriteButton: Boolean = false,
    isFavoriteButtonSelected: Boolean = false,
    onToggleFavoriteButton: () -> Unit = { }
) {
    CenterAlignedTopAppBar(
        title = { Text(text = titleText, color = textColor) },
        navigationIcon = {
            IconButton(onClick = onBackClick) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back"
                )
            }
        },
        actions = {
            if (showFavoriteButton) {
                PokedexFavoriteButton(
                    selected = isFavoriteButtonSelected,
                    onToggle = { onToggleFavoriteButton() },
                    size = 40.dp,
                    iconSize = 28.dp,
                    showBorderBlack = (textColor == Color.Black)
                )
            }
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = Color.Transparent,
            navigationIconContentColor = textColor,
            actionIconContentColor = textColor,
            titleContentColor = textColor
        )
    )
}