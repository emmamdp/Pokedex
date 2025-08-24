package com.emdp.domain.usecase.datastore

import com.emdp.domain.common.base.result.PokedexResult
import com.emdp.domain.common.base.result.PokedexResult.PkError
import com.emdp.domain.common.base.result.PokedexResult.PkSuccess
import com.emdp.domain.model.base.NoParams
import com.emdp.domain.repository.SyncPokedexRepository

class ShouldSyncPokemonListUseCaseImpl(
    private val repository: SyncPokedexRepository
) : ShouldSyncPokemonListUseCase {

    override suspend fun invoke(params: NoParams): PokedexResult<Boolean> =
        when (val lastSyncDate = repository.getLastSyncDate()) {
            is PkSuccess -> PkSuccess(pkData = (lastSyncDate.pkData == null))
            is PkError -> PkError(pkError = lastSyncDate.pkError)
        }
}