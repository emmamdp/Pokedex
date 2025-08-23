package com.emdp.data.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStoreFile
import com.emdp.data.repository.SyncPokedexRepositoryImpl
import com.emdp.data.source.local.datastore.SyncLocalDataSource
import com.emdp.data.source.local.datastore.SyncLocalDataSourceImpl
import com.emdp.domain.repository.SyncPokedexRepository
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

private const val DATASTORE_NAME = "pokedex_prefs"

val pokedexDataDiModule = module {
    single<DataStore<Preferences>> {
        PreferenceDataStoreFactory.create(
            corruptionHandler = ReplaceFileCorruptionHandler { emptyPreferences() },
            produceFile = { get<Context>().preferencesDataStoreFile(DATASTORE_NAME) }
        )
    }

    singleOf(::SyncPokedexRepositoryImpl) { bind<SyncPokedexRepository>() }
    singleOf(::SyncLocalDataSourceImpl) { bind<SyncLocalDataSource>() }
}