package com.emdp.data.source.local.datastore

import java.time.LocalDate

interface SyncLocalDataSource {
    suspend fun getLastSyncDate(): LocalDate?
    suspend fun setLastSyncDate(date: LocalDate)
}