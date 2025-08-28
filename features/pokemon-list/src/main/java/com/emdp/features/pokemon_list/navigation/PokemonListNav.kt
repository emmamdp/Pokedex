package com.emdp.features.pokemon_list.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.emdp.core.navigation.PokedexRoutes
import com.emdp.features.pokemon_list.presentation.PokemonListRoute

fun NavGraphBuilder.pokemonListScreen(onOpenDetail: (Int) -> Unit) {
    composable(PokedexRoutes.PokemonListRoute) {
        PokemonListRoute(onOpenDetail = onOpenDetail)
    }
}