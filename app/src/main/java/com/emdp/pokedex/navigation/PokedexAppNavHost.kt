package com.emdp.pokedex.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.emdp.core.navigation.PokedexRoutes
import com.emdp.features.pokemon_list.navigation.pokemonListScreen
import com.emdp.features.splash.navigation.pokedexSplashScreen

@Composable
fun PokedexAppNavHost() {
    val nav = rememberNavController()
    NavHost(navController = nav, startDestination = PokedexRoutes.SplashRoute) {
        pokedexSplashScreen(
            onOpenPokemonList = {
                nav.navigate(PokedexRoutes.PokemonListRoute) {
                    popUpTo(PokedexRoutes.SplashRoute) { inclusive = true }
                }
            }
        )
        pokemonListScreen(
            onOpenDetail = { pokemonId ->
                //nav.navigate(PokedexRoutes.PokemonDetail.build(pokemonId)) {
                //    launchSingleTop = true
                //}
            }
        )
        composable(
            route = PokedexRoutes.PokemonDetail.route,
            arguments = listOf(
                navArgument(PokedexRoutes.PokemonDetail.arg) { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val pokemonId =
                backStackEntry.arguments?.getString(PokedexRoutes.PokemonDetail.arg).orEmpty()
            // Detail Screen
        }
    }
}
