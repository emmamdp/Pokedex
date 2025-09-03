package com.emdp.core.ui.components.searchbar

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.emdp.core.ui.R
import com.emdp.core.ui.theme.PkOnPrimaryWhite

@Composable
fun PokedexSearchBar(
    query: String,
    onQueryChange: (String) -> Unit,
    onSearch: (String) -> Unit,
    modifier: Modifier = Modifier,
    placeholder: String = stringResource(R.string.pokedex_search_bar_placeholder)
) {
    val shape = RoundedCornerShape(16.dp)
    val gradient = Brush.linearGradient(
        colors = listOf(
            Color(0xFFB91C1C),
            Color(0xFFE04A4A),
            Color(0xFFFFD6DA)
        )
    )

    Box(
        modifier = modifier
            .clip(shape)
            .border(1.5.dp, PkOnPrimaryWhite, shape)
            .background(brush = gradient, shape = shape)
            .heightIn(min = 54.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        BasicTextField(
            value = query,
            onValueChange = onQueryChange,
            singleLine = true,
            textStyle = MaterialTheme.typography.bodyLarge.copy(color = PkOnPrimaryWhite),
            cursorBrush = SolidColor(PkOnPrimaryWhite),
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Search),
            keyboardActions = KeyboardActions(onSearch = { onSearch(query) }),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 14.dp, vertical = 10.dp),
            decorationBox = { inner ->
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text("🔎", color =PkOnPrimaryWhite)
                    Spacer(Modifier.width(10.dp))
                    Box(Modifier.weight(1f)) {
                        if (query.isEmpty()) {
                            Text(
                                placeholder,
                                color = PkOnPrimaryWhite.copy(alpha = 0.65f),
                                style = MaterialTheme.typography.bodyLarge
                            )
                        }
                        inner()
                    }
                    Spacer(Modifier.width(10.dp))
                    if (query.isNotEmpty()) {
                        Text(
                            "✕",
                            color = PkOnPrimaryWhite.copy(alpha = 0.9f),
                            modifier = Modifier
                                .padding(start = 6.dp)
                                .clickDeleteText { onQueryChange("") }
                        )
                    }
                }
            }
        )
    }
}

@SuppressLint("SuspiciousModifierThen")
private fun Modifier.clickDeleteText(onClick: () -> Unit) =
    this.then(
        clickable(
            indication = null,
            interactionSource = MutableInteractionSource(),
            onClick = onClick
        )
    )