package com.emdp.data.repository

import com.emdp.data.source.local.datastore.SyncLocalDataSource
import com.emdp.domain.common.base.result.PokedexResult.Error
import com.emdp.domain.common.base.result.PokedexResult.Success
import com.emdp.domain.model.error.PokedexGenericError.NoConnection
import com.emdp.domain.model.error.PokedexGenericError.UnknownError
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import java.io.IOException
import java.time.LocalDate
import kotlin.test.assertIs

internal class SyncPokedexRepositoryImplTest {

    private var local: SyncLocalDataSource = mockk()
    private lateinit var repo: SyncPokedexRepositoryImpl

    @Before
    fun setUp() {
        repo = SyncPokedexRepositoryImpl(local)
    }

    @After
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun `getLastSyncDate - success null`() = runTest {
        coEvery { local.getLastSyncDate() } returns null

        val result = repo.getLastSyncDate()

        val success = assertIs<Success<LocalDate?>>(result)
        assertNull(success.pkData)
    }

    @Test
    fun `getLastSyncDate - success with date`() = runTest {
        val today = LocalDate.now()
        coEvery { local.getLastSyncDate() } returns today

        val result = repo.getLastSyncDate()

        val success = assertIs<Success<LocalDate?>>(result)
        assertEquals(today, success.pkData)
    }

    @Test
    fun `getLastSyncDate - maps IOException to NoConnection`() = runTest {
        coEvery { local.getLastSyncDate() } throws IOException("fs read error")

        val result = repo.getLastSyncDate()

        val error = assertIs<Error>(result)
        assertEquals(NoConnection, error.pkError)
    }

    @Test
    fun `getLastSyncDate - maps unknown exception to Unknown`() = runTest {
        coEvery { local.getLastSyncDate() } throws IllegalStateException("weird state")

        val result = repo.getLastSyncDate()

        val error = assertIs<Error>(result)
        assertTrue(error.pkError is UnknownError)
    }

    @Test
    fun `setLastSyncDate - success`() = runTest {
        val date = LocalDate.now()
        coEvery { local.setLastSyncDate(date) } returns Unit

        val result = repo.setLastSyncDate(date)

        assertIs<Success<Unit>>(result)
    }

    @Test
    fun `setLastSyncDate - error maps to Unknown`() = runTest {
        val date = LocalDate.now()
        coEvery { local.setLastSyncDate(date) } throws RuntimeException("boom")

        val result = repo.setLastSyncDate(date)

        val error = assertIs<Error>(result)
        assertTrue(error.pkError is UnknownError)
    }
}