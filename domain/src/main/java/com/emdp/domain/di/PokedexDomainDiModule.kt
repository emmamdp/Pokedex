package com.emdp.domain.di

import com.emdp.domain.usecase.datastore.ShouldSyncPokemonTodayUseCase
import com.emdp.domain.usecase.datastore.ShouldSyncPokemonTodayUseCaseImpl
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val pokedexDomainDiModule = module {
    factoryOf(::ShouldSyncPokemonTodayUseCaseImpl) { bind<ShouldSyncPokemonTodayUseCase>() }
}