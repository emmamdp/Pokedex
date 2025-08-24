package com.emdp.core.di

import com.emdp.core.common.di.pokedexCommonDiModule
import com.emdp.data.di.pokedexDataDiModule
import com.emdp.data.di.pokedexDataStoreDiModule
import com.emdp.data.di.pokedexNetworkModule
import com.emdp.domain.di.pokedexDomainDiModule
import com.emdp.features.pokemon_detail.di.pokemonDetailDiModule
import com.emdp.features.pokemon_list.di.pokemonListDiModule
import com.emdp.features.splash.di.pokedexSplashDiModule
import org.koin.core.module.Module

fun allModules(): List<Module> = listOf(
    pokedexCommonDiModule,
    pokedexDataStoreDiModule,
    pokedexNetworkModule,
    pokedexDataDiModule,
    pokedexDomainDiModule,
    pokedexSplashDiModule,
    pokemonListDiModule,
    pokemonDetailDiModule
)