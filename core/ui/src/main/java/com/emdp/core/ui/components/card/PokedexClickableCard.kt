package com.emdp.core.ui.components.card

import android.view.MotionEvent
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.unit.dp
import com.emdp.core.ui.theme.PkOnPrimaryWhite

@Composable
fun PokedexClickableCard(
    modifier: Modifier = Modifier,
    shape: RoundedCornerShape = RoundedCornerShape(28.dp),
    isButtonClickable: Boolean = true,
    onClick: () -> Unit = {},
    content: @Composable () -> Unit
) {
    var isClicked by remember { mutableStateOf(false) }
    val scale by animateFloatAsState(
        targetValue = if (isClicked) 0.95f else 1f,
        label = "card_scale"
    )

    val enabledAlpha = if (isButtonClickable) 1f else 0.6f

    Card(
        colors = CardDefaults.cardColors(containerColor = Color.Transparent),
        elevation = CardDefaults.elevatedCardElevation(),
        shape = shape,
        modifier = modifier
            .fillMaxWidth()
            .graphicsLayer {
                scaleX = scale
                scaleY = scale
            }
            .then(
                other = if (isButtonClickable) {
                    Modifier
                        .clickable(
                            onClick = onClick,
                            indication = null,
                            interactionSource = remember { MutableInteractionSource() }
                        )
                        .pointerInteropFilter {
                            when (it.action) {
                                MotionEvent.ACTION_DOWN -> isClicked = true
                                MotionEvent.ACTION_UP,
                                MotionEvent.ACTION_CANCEL -> isClicked = false
                            }
                            false
                        }
                } else Modifier
            )
            .alpha(enabledAlpha)
            .border(
                width = 1.dp,
                color = PkOnPrimaryWhite,
                shape = shape
            )
    ) {
        content()
    }
}