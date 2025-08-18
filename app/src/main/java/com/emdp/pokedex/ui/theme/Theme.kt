package com.emdp.pokedex.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable

private val PokedexDarkColors = darkColorScheme(
    primary = PkPrimary,
    onPrimary = PkOnPrimary,
    secondary = PkSecondary,
    onSecondary = PkOnSecondary,
    background = PkBackground,
    surface = PkSurface,
    onSurface = PkOnSurface,
    surfaceVariant = PkSurfaceVariant,
    onSurfaceVariant = PkOnSurfaceVariant
)

@Composable
fun PokedexTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = PokedexDarkColors,
        typography = PokedexTypography,
        shapes = PokedexShapes,
        content = content
    )
}