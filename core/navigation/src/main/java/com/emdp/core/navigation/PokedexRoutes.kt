package com.emdp.core.navigation

object PokedexRoutes {
    const val SplashRoute = "pokedex/splash"
    const val PokemonListRoute = "pokedex/pokemonList"

    object PokemonDetail {
        const val arg = "pokemonId"
        const val route = "pokemon/detail/{$arg}"
        fun build(pokemonId: String) = "pokemon/detail/$pokemonId"
    }
}