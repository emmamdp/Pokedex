package com.emdp.core.di

import com.emdp.core.common.di.pokedexCommonDiModule
import com.emdp.data.di.pokedexDataDiModule
import com.emdp.features.pokemon_list.di.pokemonListDiModule
import com.emdp.features.splash.di.pokedexSplashDiModule
import org.koin.core.module.Module

fun allModules(): List<Module> = listOf(
    pokedexCommonDiModule,
    pokedexDataDiModule,
    pokedexSplashDiModule,
    pokemonListDiModule
)