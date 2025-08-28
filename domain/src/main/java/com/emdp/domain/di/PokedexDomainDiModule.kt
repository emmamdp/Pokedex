package com.emdp.domain.di

import com.emdp.domain.usecase.datastore.ShouldSyncPokemonListUseCase
import com.emdp.domain.usecase.datastore.ShouldSyncPokemonListUseCaseImpl
import com.emdp.domain.usecase.pokemonlist.GetPokemonListUseCase
import com.emdp.domain.usecase.pokemonlist.GetPokemonListUseCaseImpl
import com.emdp.domain.usecase.pokemonlist.SyncPokemonListUseCase
import com.emdp.domain.usecase.pokemonlist.SyncPokemonListUseCaseImpl
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val pokedexDomainDiModule = module {
    factoryOf(::ShouldSyncPokemonListUseCaseImpl) { bind<ShouldSyncPokemonListUseCase>() }
    factoryOf(::SyncPokemonListUseCaseImpl) { bind<SyncPokemonListUseCase>() }
    factoryOf(::GetPokemonListUseCaseImpl) { bind<GetPokemonListUseCase>() }
}