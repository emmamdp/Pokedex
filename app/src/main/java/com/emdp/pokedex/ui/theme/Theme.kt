package com.emdp.pokedex.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import com.emdp.core.ui.theme.PkBackground
import com.emdp.core.ui.theme.PkOnPrimaryWhite
import com.emdp.core.ui.theme.PkOnSecondary
import com.emdp.core.ui.theme.PkOnSurface
import com.emdp.core.ui.theme.PkOnSurfaceVariant
import com.emdp.core.ui.theme.PkPrimaryRed
import com.emdp.core.ui.theme.PkSecondaryBlue
import com.emdp.core.ui.theme.PkSurface
import com.emdp.core.ui.theme.PkSurfaceVariant

private val PokedexDarkColors = darkColorScheme(
    primary = PkPrimaryRed,
    onPrimary = PkOnPrimaryWhite,
    secondary = PkSecondaryBlue,
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