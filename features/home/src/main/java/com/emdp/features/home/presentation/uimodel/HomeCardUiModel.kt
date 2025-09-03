package com.emdp.features.home.presentation.uimodel

import androidx.compose.ui.graphics.Color
import com.emdp.features.home.presentation.common.PokedexHomeButtonDestinations

data class HomeCardUiModel(
    val title: Int,
    val destination: PokedexHomeButtonDestinations,
    val enabled: Boolean,
    val gradient: List<Color>,
    val imageRes: Int
)