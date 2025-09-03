package com.emdp.core.ui.components.chip

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.emdp.core.ui.theme.PkOnPrimaryWhite
import com.emdp.core.ui.theme.PokemonTypeChipColor
import com.emdp.core.ui.theme.PokemonTypeColor
import com.emdp.domain.model.types.PokemonType
import kotlin.collections.forEach

@Composable
fun PokedexTypeChips(
    types: List<PokemonType>,
    onChipClick: (PokemonType) -> Unit,
    borderColor: Color = PkOnPrimaryWhite
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        types.forEach { type ->
            val container = PokemonTypeChipColor.vibrantFor(type)
            val label = PokemonTypeColor.onTextColorFor(container)

            AssistChip(
                onClick = { onChipClick(type) },
                label = { Text(type.displayName()) },
                colors = AssistChipDefaults.assistChipColors(
                    containerColor = container,
                    labelColor = label,
                    leadingIconContentColor = label,
                    trailingIconContentColor = label
                ),
                border = AssistChipDefaults.assistChipBorder(
                    enabled = true,
                    borderColor = borderColor,
                    disabledBorderColor = PkOnPrimaryWhite.copy(alpha = 0.4f),
                    borderWidth = 1.dp
                ),
                elevation = AssistChipDefaults.assistChipElevation(
                    elevation = 4.dp,
                    pressedElevation = 6.dp,
                    focusedElevation = 4.dp,
                    hoveredElevation = 6.dp,
                    disabledElevation = 0.dp
                )
            )
        }
    }
}