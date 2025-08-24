package com.emdp.domain.di

import com.emdp.domain.usecase.datastore.ShouldSyncPokemonTodayUseCase
import com.emdp.domain.usecase.datastore.ShouldSyncPokemonTodayUseCaseImpl
import com.emdp.domain.usecase.pokemonlist.SyncPokemonListUseCase
import com.emdp.domain.usecase.pokemonlist.SyncPokemonListUseCaseImpl
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val pokedexDomainDiModule = module {
    factoryOf(::ShouldSyncPokemonTodayUseCaseImpl) { bind<ShouldSyncPokemonTodayUseCase>() }
    factoryOf(::SyncPokemonListUseCaseImpl) { bind<SyncPokemonListUseCase>() }
}