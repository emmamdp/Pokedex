package com.emdp.domain.usecase.pokemonlist

import com.emdp.domain.common.base.result.PokedexResult
import com.emdp.domain.model.base.NoParams
import com.emdp.domain.repository.PokedexRepository
import com.emdp.domain.repository.SyncPokedexRepository
import java.time.LocalDate

class SyncPokemonListUseCaseImpl(
    private val repository: PokedexRepository,
    private val syncPokedexRepository: SyncPokedexRepository
) : SyncPokemonListUseCase {

    override suspend fun invoke(params: NoParams): PokedexResult<Unit> =
        when (val result = repository.syncPokemonList()) {
            is PokedexResult.PkSuccess -> setSyncDate()
            is PokedexResult.PkError -> result
        }

    private suspend fun setSyncDate() =
        when (val resultSync = syncPokedexRepository.setLastSyncDate(LocalDate.now())) {
            is PokedexResult.PkSuccess -> resultSync
            is PokedexResult.PkError -> resultSync
        }
}