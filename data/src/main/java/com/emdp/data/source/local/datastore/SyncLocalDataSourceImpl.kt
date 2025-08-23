package com.emdp.data.source.local.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.first
import java.time.LocalDate

class SyncLocalDataSourceImpl(
    private val dataStore: DataStore<Preferences>
) : SyncLocalDataSource {

    override suspend fun getLastSyncDate(): LocalDate? {
        val prefs = dataStore.data.first()
        val iso = prefs[SyncPreferencesKeys.LAST_SYNC_DATE]
        return LocalDateIso.parseOrNull(iso)
    }

    override suspend fun setLastSyncDate(date: LocalDate) {
        dataStore.edit { prefs ->
            prefs[SyncPreferencesKeys.LAST_SYNC_DATE] = LocalDateIso.toIso(date)
        }
    }
}