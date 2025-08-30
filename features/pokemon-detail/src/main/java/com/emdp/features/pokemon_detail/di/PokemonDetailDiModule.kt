package com.emdp.features.pokemon_detail.di

import com.emdp.features.pokemon_detail.presentation.PokemonDetailViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val pokemonDetailDiModule = module {
    viewModelOf(::PokemonDetailViewModel)
}