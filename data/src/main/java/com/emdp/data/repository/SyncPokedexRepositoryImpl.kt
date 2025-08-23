package com.emdp.data.repository

import com.emdp.data.common.base.safeCall
import com.emdp.data.source.local.datastore.SyncLocalDataSource
import com.emdp.domain.common.base.result.PokedexResult
import com.emdp.domain.repository.SyncPokedexRepository
import java.time.LocalDate

class SyncPokedexRepositoryImpl(
    private val syncLocalSource: SyncLocalDataSource
) : SyncPokedexRepository {

    override suspend fun getLastSyncDate(): PokedexResult<LocalDate?> =
        safeCall { syncLocalSource.getLastSyncDate() }

    override suspend fun setLastSyncDate(date: LocalDate): PokedexResult<Unit> =
        safeCall { syncLocalSource.setLastSyncDate(date) }
}