package com.emdp.core.ui.components.background

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.emdp.core.ui.R
import com.emdp.core.ui.theme.PkDarkRed
import com.emdp.core.ui.theme.PkOnPrimaryWhite

@Composable
fun BackgroundPokeball(
    modifier: Modifier,
    contentAlignment: Alignment = Alignment.BottomEnd,
    backgroundColor: Color = PkDarkRed,
    size: Dp = 80.dp
) {
    val bgLum = backgroundColor.luminance()
    val tintAlpha = (0.06f + bgLum * 0.10f)

    Box(
        modifier = modifier,
        contentAlignment = contentAlignment
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_pokeball),
            contentDescription = null,
            colorFilter = ColorFilter.tint(PkOnPrimaryWhite.copy(alpha = tintAlpha)),
            modifier = Modifier
                .aspectRatio(
                    ratio = 1f,
                    matchHeightConstraintsFirst = true
                )
                .graphicsLayer {
                    clip = false
                    scaleX = 1.5f
                    scaleY = 1.5f
                    rotationZ = -30f
                }
                .offset(x = size),
            contentScale = ContentScale.Fit
        )
    }
}