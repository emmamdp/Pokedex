package com.emdp.core.ui.components.card

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.lerp
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.emdp.core.ui.components.favorite.PokedexFavoriteButton
import com.emdp.core.ui.theme.PkOnPrimaryWhite

@Composable
fun PokedexCard(
    name: String,
    subtitle: String,
    onItemClick: () -> Unit,
    isFavorite: Boolean,
    onToggleFavorite: () -> Unit
) {
    val gradient = itemGradient()

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(6.dp, ItemShape, clip = false)
            .clip(ItemShape)
            .background(gradient)
            .border(1.dp, PkOnPrimaryWhite.copy(alpha = 0.65f), ItemShape)
            .clickable(onClick = onItemClick)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp, horizontal = 12.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            ThunderboltCircle()

            ItemText(name, subtitle, Modifier.weight(1f))

            PokedexFavoriteButton(
                selected = isFavorite,
                onToggle = { onToggleFavorite() },
            )
        }
    }
}

@Composable
private fun ThunderboltCircle() {
    val cs = MaterialTheme.colorScheme
    Box(
        modifier = Modifier
            .size(56.dp)
            .shadow(6.dp, CircleShape, clip = false)
            .clip(CircleShape)
            .background(cs.primary, CircleShape)
            .border(2.dp, PkOnPrimaryWhite, CircleShape),
        contentAlignment = Alignment.Center
    ) {
        Text("⚡", color = cs.onPrimary, style = MaterialTheme.typography.titleMedium)
    }
}

@Composable
private fun ItemText(primaryText: String, secondaryText: String, modifier: Modifier) {
    Column(modifier = modifier) {
        Text(
            text = primaryText.replaceFirstChar { it.uppercase() },
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            color = PkOnPrimaryWhite
        )
        Spacer(Modifier.height(4.dp))
        Text(
            text = secondaryText,
            style = MaterialTheme.typography.bodyMedium,
            color = PkOnPrimaryWhite.copy(alpha = 0.85f)
        )
    }
}

@Composable
private fun itemGradient(): Brush =
    if (!isSystemInDarkTheme()) {
        val start = lerp(Color(0xFFF97316), Color.Black, 0.25f)
        val end = lerp(Color(0xFFB91C1C), Color.Black, 0.20f)
        Brush.linearGradient(listOf(start, end))
    } else {
        Brush.linearGradient(listOf(Color(0xFF7F1D1D), Color(0xFF1F2937)))
    }

private val ItemShape = RoundedCornerShape(20.dp)