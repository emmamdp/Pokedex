package com.emdp.data.source.local.datastore

import androidx.datastore.preferences.core.stringPreferencesKey

internal object SyncPreferencesKeys {
    val LAST_SYNC_DATE = stringPreferencesKey("last_sync_date")
}