package com.emdp.features.pokemon_list.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.emdp.features.pokemon_list.presentation.PokemonListScreen

object PokemonListRoute { const val pokemonListRoute = "pokemon/list" }

fun NavGraphBuilder.pokemonListScreen(onOpenDetail: (String) -> Unit) {
    composable(PokemonListRoute.pokemonListRoute) { PokemonListScreen(onOpenDetail) }
}