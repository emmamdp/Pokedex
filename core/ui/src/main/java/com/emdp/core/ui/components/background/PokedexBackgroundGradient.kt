package com.emdp.core.ui.components.background

import androidx.compose.runtime.Composable
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import com.emdp.core.ui.theme.PkBackgroundGradient


@Composable
fun pokedexBackgroundGradient(): Brush =
    Brush.linearGradient(
        colors = PkBackgroundGradient,
        start = Offset(0f, 0f),
        end = Offset(Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY)
    )