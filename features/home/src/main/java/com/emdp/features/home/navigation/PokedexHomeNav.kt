package com.emdp.features.home.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.emdp.core.navigation.PokedexRoutes
import com.emdp.features.home.presentation.PokedexHomeScreen

fun NavGraphBuilder.pokedexHomeScreen(onOpenPokemonList: () -> Unit) {
    composable(PokedexRoutes.HomeRoute) {
        PokedexHomeScreen(onOpenPokemonList)
    }
}