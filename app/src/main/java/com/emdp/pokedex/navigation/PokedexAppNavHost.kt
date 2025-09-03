package com.emdp.pokedex.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.emdp.core.navigation.PokedexRoutes
import com.emdp.features.home.navigation.pokedexHomeScreen
import com.emdp.features.pokemon_detail.presentation.PokemonDetailScreen
import com.emdp.features.pokemon_list.navigation.pokemonListScreen
import com.emdp.features.splash.navigation.pokedexSplashScreen

@Composable
fun PokedexAppNavHost() {
    val nav = rememberNavController()
    NavHost(navController = nav, startDestination = PokedexRoutes.SplashRoute) {
        pokedexSplashScreen(
            navigateToHomeScreen = {
                nav.navigate(PokedexRoutes.HomeRoute) {
                    popUpTo(PokedexRoutes.SplashRoute) { inclusive = true }
                }
            }
        )
        pokedexHomeScreen(
            onOpenPokemonList = {
                nav.navigate(PokedexRoutes.PokemonListRoute) { launchSingleTop = true }
            }
        )
        pokemonListScreen(
            onOpenDetail = { pokemonId: Int ->
                nav.navigate(PokedexRoutes.PokemonDetail.build(pokemonId)) {
                    launchSingleTop = true
                }
            },
            onBackClick = { nav.popBackStack() }
        )

        composable(
            route = PokedexRoutes.PokemonDetail.route,
            arguments = listOf(
                navArgument(PokedexRoutes.PokemonDetail.arg) { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val pokemonId = backStackEntry.arguments?.getInt(
                PokedexRoutes.PokemonDetail.arg
            ) ?: return@composable

            PokemonDetailScreen(
                id = pokemonId,
                onBackClick = { nav.popBackStack() }
            )
        }
    }
}