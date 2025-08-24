package com.emdp.domain.repository

import com.emdp.domain.common.base.result.PokedexResult

interface PokedexRepository {
    suspend fun syncPokemonList(): PokedexResult<Unit>
}