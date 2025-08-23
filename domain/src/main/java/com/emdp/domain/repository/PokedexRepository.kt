package com.emdp.domain.repository

import com.emdp.domain.common.base.result.PokedexResult
import java.time.LocalDate

interface PokedexRepository {
    suspend fun getLastSyncDate(): PokedexResult<LocalDate?>
    suspend fun setLastSyncDate(date: LocalDate)
}