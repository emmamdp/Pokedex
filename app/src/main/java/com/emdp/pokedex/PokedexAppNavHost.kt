package com.emdp.pokedex

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.emdp.features.pokemon_list.navigation.PokemonListRoute
import com.emdp.features.pokemon_list.navigation.pokemonListScreen

@Composable
fun PokedexAppNavHost() {
    val nav = rememberNavController()
    NavHost(navController = nav, startDestination = PokemonListRoute.pokemonListRoute) {
        pokemonListScreen(
            onOpenDetail = { pokemonId ->
                nav.navigate("pokemon/detail/$pokemonId")
            }
        )
        composable(
            route = "pokemon/detail/{pokemonId}",
            arguments = listOf(navArgument("pokemonId") { type = NavType.StringType })
        ) { backStackEntry ->
            val pokemonId = backStackEntry.arguments?.getString("pokemonId").orEmpty()
            // Detail Screen
        }
    }
}
