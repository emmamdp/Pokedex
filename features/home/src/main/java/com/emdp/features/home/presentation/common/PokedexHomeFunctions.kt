package com.emdp.features.home.presentation.common

import com.emdp.core.ui.R
import com.emdp.core.ui.theme.PkHomeCardGradient01
import com.emdp.core.ui.theme.PkHomeCardGradient02
import com.emdp.core.ui.theme.PkHomeCardGradient03
import com.emdp.core.ui.theme.PkHomeCardGradient04
import com.emdp.features.home.presentation.uimodel.HomeCardUiModel

val homeCards = listOf(
    HomeCardUiModel(
        title = R.string.pokedex_home_card_pokemons,
        destination = PokedexHomeButtonDestinations.PokemonList,
        enabled = true,
        gradient = PkHomeCardGradient01,
        imageRes = R.drawable.ic_pokeballs
    ),
    HomeCardUiModel(
        title = R.string.pokedex_home_card_favorites,
        destination = PokedexHomeButtonDestinations.Favorites,
        enabled = false,
        gradient = PkHomeCardGradient02,
        imageRes = R.drawable.ic_favorite
    ),
    HomeCardUiModel(
        title = R.string.pokedex_home_card_types,
        destination = PokedexHomeButtonDestinations.Types,
        enabled = false,
        gradient = PkHomeCardGradient03,
        imageRes = R.drawable.ic_type
    ),
    HomeCardUiModel(
        title = R.string.pokedex_home_card_moves,
        destination = PokedexHomeButtonDestinations.Moves,
        enabled = false,
        gradient = PkHomeCardGradient04,
        imageRes = R.drawable.ic_fight
    )
)
