package com.emdp.data.repository

import com.emdp.data.common.base.safeCall
import com.emdp.data.source.local.database.PokemonLocalDataSource
import com.emdp.data.source.remote.PokemonRemoteDataSource
import com.emdp.domain.common.base.result.PokedexResult
import com.emdp.domain.common.base.result.PokedexResult.PkError
import com.emdp.domain.common.base.result.PokedexResult.PkSuccess
import com.emdp.domain.model.PokemonListModel
import com.emdp.domain.repository.PokedexRepository

class PokedexRepositoryImpl(
    private val remote: PokemonRemoteDataSource,
    private val local: PokemonLocalDataSource
) : PokedexRepository {

    override suspend fun syncPokemonList(): PokedexResult<Unit> =
        when (val result = remote.getAllPokemon()) {
            is PkSuccess -> insertAllPokemonInDatabase(result.pkData)
            is PkError -> result
        }

    private suspend fun insertAllPokemonInDatabase(modelList: List<PokemonListModel>) =
        when (val insertResult = safeCall { local.insertAll(modelList) }) {
            is PkSuccess -> PkSuccess(Unit)
            is PkError -> insertResult
        }
}