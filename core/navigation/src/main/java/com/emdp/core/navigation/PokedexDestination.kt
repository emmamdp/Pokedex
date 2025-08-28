package com.emdp.core.navigation

sealed interface PokedexDestination {
    data object OpenPokemonList : PokedexDestination
    data class OpenPokemonDetail(val id: Int) : PokedexDestination
}