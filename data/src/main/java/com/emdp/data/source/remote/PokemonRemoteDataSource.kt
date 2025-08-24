package com.emdp.data.source.remote

import com.emdp.domain.common.base.result.PokedexResult
import com.emdp.domain.model.PokemonListModel

interface PokemonRemoteDataSource {
    suspend fun getAllPokemon(): PokedexResult<List<PokemonListModel>>
}