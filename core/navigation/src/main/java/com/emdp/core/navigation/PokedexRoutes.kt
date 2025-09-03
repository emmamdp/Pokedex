package com.emdp.core.navigation

object PokedexRoutes {
    const val SplashRoute = "pokedex/splash"
    const val HomeRoute = "pokedex/home"
    const val PokemonListRoute = "pokedex/pokemonList"

    object PokemonDetail {
        const val arg = "pokemonId"
        const val route = "pokemon/detail/{$arg}"
        fun build(pokemonId: Int) = "pokemon/detail/$pokemonId"
    }
}