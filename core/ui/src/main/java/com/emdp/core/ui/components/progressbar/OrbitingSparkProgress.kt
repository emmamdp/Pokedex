package com.emdp.core.ui.components.progressbar

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.min
import kotlin.math.sin

@Composable
fun OrbitingSparkProgress(
    modifier: Modifier = Modifier,
    strokeWidth: Dp = 6.dp,
    trackColor: Color = Color(0x992A75BB),
    sparkColor: Color = Color(0xFFFFCC00),
    speedMillis: Int = 1400
) {
    val transition = rememberInfiniteTransition(label = "orbit")
    val angle by transition.animateFloat(
        initialValue = 0f,
        targetValue = (2f * PI).toFloat(),
        animationSpec = infiniteRepeatable(
            tween(
                durationMillis = speedMillis,
                easing = LinearEasing
            )
        ),
        label = "angle"
    )
    val pulse by transition.animateFloat(
        initialValue = 0.85f,
        targetValue = 1.15f,
        animationSpec = infiniteRepeatable(tween(durationMillis = 900, easing = LinearEasing)),
        label = "pulse"
    )

    Canvas(modifier = modifier) {
        val sw = strokeWidth.toPx()
        val r = (min(size.width, size.height) - sw) / 2f
        val center = Offset(size.width / 2f, size.height / 2f)

        drawCircle(
            color = trackColor,
            radius = r,
            center = center,
            style = Stroke(width = sw, cap = StrokeCap.Round)
        )

        val cx = center.x + cos(angle) * r
        val cy = center.y + sin(angle) * r
        val sparkRadius = sw * 0.7f * pulse

        drawCircle(
            color = sparkColor.copy(alpha = 0.22f),
            radius = sparkRadius * 2.1f,
            center = Offset(cx, cy)
        )
        drawCircle(
            color = sparkColor,
            radius = sparkRadius,
            center = Offset(cx, cy)
        )
    }
}