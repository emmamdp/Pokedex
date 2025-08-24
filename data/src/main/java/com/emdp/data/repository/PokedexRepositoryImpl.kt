package com.emdp.data.repository

import com.emdp.data.source.remote.PokemonRemoteDataSource
import com.emdp.domain.common.base.result.PokedexResult
import com.emdp.domain.repository.PokedexRepository

class PokedexRepositoryImpl(
    private val dataSource: PokemonRemoteDataSource
) : PokedexRepository {

    override suspend fun syncPokemonList(): PokedexResult<Unit> {
        dataSource.getAllPokemon()
        // TODO: set data in database
        return PokedexResult.PkSuccess(Unit)
    }
}