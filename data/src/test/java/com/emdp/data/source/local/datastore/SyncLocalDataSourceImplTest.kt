package com.emdp.data.source.local.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.emptyPreferences
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test
import java.io.File
import java.nio.file.Files
import java.time.LocalDate

internal class SyncLocalDataSourceImplTest {

    private lateinit var tempDirectory: File
    private lateinit var dataStore: DataStore<Preferences>
    private lateinit var syncLocalSource: SyncLocalDataSource

    @Before
    fun setUp() {
        tempDirectory = Files.createTempDirectory("ds_test").toFile()
        dataStore = PreferenceDataStoreFactory.create(
            corruptionHandler = ReplaceFileCorruptionHandler { emptyPreferences() },
            produceFile = { File(tempDirectory, "sync_prefs.preferences_pb") }
        )
        syncLocalSource = SyncLocalDataSourceImpl(dataStore)
    }

    @After
    fun tearDown() {
        tempDirectory.deleteRecursively()
    }

    @Test
    fun `getLastSyncDate returns null when empty`() = runTest {
        val result = syncLocalSource.getLastSyncDate()

        assertNull(result)
    }

    @Test
    fun `setLastSyncDate sets a date then getLastSyncDate returns same date`() =
        runTest {
            val today = LocalDate.now()

            syncLocalSource.setLastSyncDate(today)
            val read = syncLocalSource.getLastSyncDate()

            assertEquals(today, read)
        }
}