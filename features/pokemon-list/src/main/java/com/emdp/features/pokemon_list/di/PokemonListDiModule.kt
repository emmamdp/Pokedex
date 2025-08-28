package com.emdp.features.pokemon_list.di

import com.emdp.features.pokemon_list.presentation.PokemonListViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val pokemonListDiModule = module {
    viewModelOf(::PokemonListViewModel)
}