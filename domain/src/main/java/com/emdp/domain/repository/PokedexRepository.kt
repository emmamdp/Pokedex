package com.emdp.domain.repository

import com.emdp.domain.common.base.result.PokedexResult
import androidx.paging.PagingData
import com.emdp.domain.model.PokemonListModel
import kotlinx.coroutines.flow.Flow

interface PokedexRepository {
    suspend fun syncPokemonList(): PokedexResult<Unit>
    fun getPagedPokemonList(): Flow<PagingData<PokemonListModel>>
}