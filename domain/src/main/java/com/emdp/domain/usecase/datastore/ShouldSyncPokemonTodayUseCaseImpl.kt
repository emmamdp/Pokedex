package com.emdp.domain.usecase.datastore

import com.emdp.domain.common.base.result.PokedexResult
import com.emdp.domain.common.base.result.PokedexResult.PkError
import com.emdp.domain.common.base.result.PokedexResult.PkSuccess
import com.emdp.domain.model.base.NoParams
import com.emdp.domain.repository.SyncPokedexRepository
import java.time.LocalDate

class ShouldSyncPokemonTodayUseCaseImpl(
    private val repository: SyncPokedexRepository
) : ShouldSyncPokemonTodayUseCase {

    override suspend fun invoke(params: NoParams): PokedexResult<Boolean> =
        when (val lastSyncDate = repository.getLastSyncDate()) {
            is PkSuccess -> {
                val today = LocalDate.now()
                lastSyncDate.pkData.let { lastSync ->
                    val isSyncToday = (lastSync == null || lastSync.isBefore(today))
                    PkSuccess(pkData = isSyncToday)
                }
            }

            is PkError -> PkError(pkError = lastSyncDate.pkError)
        }
}