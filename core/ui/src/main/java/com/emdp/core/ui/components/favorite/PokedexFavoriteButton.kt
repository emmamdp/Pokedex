package com.emdp.core.ui.components.favorite

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.stateDescription
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.emdp.core.ui.R

@Composable
fun PokedexFavoriteButton(
    selected: Boolean,
    onToggle: () -> Unit,
    size: Dp = 48.dp,
    iconSize: Dp = 32.dp,
    showBorderBlack: Boolean = false
) {
    val icon = if (showBorderBlack)
        getFavoriteBlackIcon(selected)
    else
        getFavoriteIcon(selected)

    val favoriteText = stringResource(R.string.pokedex_favorite_button_selected)
    val stateDescriptionText = stringResource(
        if (selected)
            R.string.pokedex_favorite_button_activated
        else
            R.string.pokedex_favorite_button_desactivated
    )
    Box(
        modifier = Modifier
            .size(size)
            .clip(CircleShape)
            .clickable(role = Role.Button, onClick = onToggle)
            .semantics {
                contentDescription = favoriteText
                stateDescription = stateDescriptionText
            },
        contentAlignment = Alignment.Center
    ) {
        Icon(
            painter = icon,
            contentDescription = null,
            modifier = Modifier.size(iconSize),
            tint = Color.Unspecified
        )
    }
}

@Composable
private fun getFavoriteIcon(selected: Boolean) =
    if (selected)
        painterResource(R.drawable.favorite_selected)
    else
        painterResource(R.drawable.favorite_unselected)

@Composable
private fun getFavoriteBlackIcon(selected: Boolean) =
    if (selected)
        painterResource(R.drawable.favorite_selected_black)
    else
        painterResource(R.drawable.favorite_unselected_black)