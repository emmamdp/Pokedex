package com.emdp.data.source.remote

import com.emdp.data.common.base.safeCall
import com.emdp.data.source.remote.api.PokeApiService
import com.emdp.data.source.remote.mapper.PokemonRemoteMapper
import com.emdp.domain.common.base.result.PokedexResult
import com.emdp.domain.model.PokemonDetailModel
import com.emdp.domain.model.PokemonListModel

class PokemonRemoteDataSourceImpl(
    private val api: PokeApiService,
    private val mapper: PokemonRemoteMapper
) : PokemonRemoteDataSource {

    override suspend fun getAllPokemon(): PokedexResult<List<PokemonListModel>> =
        safeCall {
            val pokemonListDto = api.getPokemonList(offset = 0, limit = ALL_LIMIT)
            mapper.toModel(responseDto = pokemonListDto)
        }

    override suspend fun getPokemonDetail(pokemonId: Int): PokedexResult<PokemonDetailModel> =
        safeCall {
            val pokemonDetailDto = api.getPokemonDetail(pokemonId)
            mapper.toModel(responseDto = pokemonDetailDto)
        }

    private companion object {
        const val ALL_LIMIT = 100_000
    }
}