package com.emdp.core.ui.components.pill

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun PokedexInfoPill(label: String, value: String, onColor: Color) {
    OutlinedCard(
        border = BorderStroke(width = 1.dp, color = onColor),
        colors = CardDefaults.outlinedCardColors(containerColor = Color.White.copy(alpha = 0.10f))
    ) {
        Column(
            modifier = Modifier.padding(vertical = 10.dp, horizontal = 14.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = label,
                style = MaterialTheme.typography.labelMedium,
                color = onColor.copy(alpha = 0.9f)
            )
            Text(
                text = value,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold,
                color = onColor
            )
        }
    }
}