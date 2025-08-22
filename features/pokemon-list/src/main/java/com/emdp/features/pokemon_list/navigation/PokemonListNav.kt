package com.emdp.features.pokemon_list.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.emdp.core.navigation.PokedexRoutes
import com.emdp.features.pokemon_list.presentation.PokemonListScreen

fun NavGraphBuilder.pokemonListScreen(onOpenDetail: (String) -> Unit) {
    composable(PokedexRoutes.PokemonListRoute) { PokemonListScreen(onOpenDetail) }
}