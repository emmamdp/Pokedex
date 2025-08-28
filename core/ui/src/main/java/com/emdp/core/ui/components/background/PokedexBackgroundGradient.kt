package com.emdp.core.ui.components.background

import androidx.compose.runtime.Composable
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color


@Composable
fun pokedexBackgroundGradient(): Brush =
    Brush.linearGradient(
        colors = listOf(
            Color(0xFFB3261E),
            Color(0xFF6B2E5E),
            Color(0xFF2A3442)
        ),
        start = Offset(0f, 0f),
        end = Offset(Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY)
    )