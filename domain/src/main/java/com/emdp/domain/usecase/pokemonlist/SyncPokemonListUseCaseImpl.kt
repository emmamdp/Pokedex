package com.emdp.domain.usecase.pokemonlist

import com.emdp.domain.common.base.result.PokedexResult
import com.emdp.domain.model.base.NoParams
import com.emdp.domain.repository.PokedexRepository

class SyncPokemonListUseCaseImpl(
    private val repository: PokedexRepository
) : SyncPokemonListUseCase {

    override suspend fun invoke(params: NoParams): PokedexResult<Unit> =
        repository.syncPokemonList()
}