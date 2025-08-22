package com.emdp.features.splash.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.emdp.core.navigation.PokedexRoutes
import com.emdp.features.splash.presentation.PokedexSplashScreen

fun NavGraphBuilder.pokedexSplashScreen(onOpenPokemonList: () -> Unit) {
    composable(PokedexRoutes.SplashRoute) {
        PokedexSplashScreen(onOpenPokemonList)
    }
}