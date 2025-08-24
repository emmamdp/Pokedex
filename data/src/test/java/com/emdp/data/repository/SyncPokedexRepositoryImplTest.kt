package com.emdp.data.repository

import com.emdp.data.source.local.datastore.SyncLocalDataSource
import com.emdp.domain.common.base.result.PokedexResult.PkError
import com.emdp.domain.common.base.result.PokedexResult.PkSuccess
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

        val pkSuccess = assertIs<PkSuccess<LocalDate?>>(result)
        assertNull(pkSuccess.pkData)
    }

    @Test
    fun `getLastSyncDate - success with date`() = runTest {
        val today = LocalDate.now()
        coEvery { local.getLastSyncDate() } returns today

        val result = repo.getLastSyncDate()

        val pkSuccess = assertIs<PkSuccess<LocalDate?>>(result)
        assertEquals(today, pkSuccess.pkData)
    }

    @Test
    fun `getLastSyncDate - maps IOException to NoConnection`() = runTest {
        coEvery { local.getLastSyncDate() } throws IOException("fs read error")

        val result = repo.getLastSyncDate()

        val pkError = assertIs<PkError>(result)
        assertEquals(NoConnection, pkError.pkError)
    }

    @Test
    fun `getLastSyncDate - maps unknown exception to Unknown`() = runTest {
        coEvery { local.getLastSyncDate() } throws IllegalStateException("weird state")

        val result = repo.getLastSyncDate()

        val pkError = assertIs<PkError>(result)
        assertTrue(pkError.pkError is UnknownError)
    }

    @Test
    fun `setLastSyncDate - success`() = runTest {
        val date = LocalDate.now()
        coEvery { local.setLastSyncDate(date) } returns Unit

        val result = repo.setLastSyncDate(date)

        assertIs<PkSuccess<Unit>>(result)
    }

    @Test
    fun `setLastSyncDate - error maps to Unknown`() = runTest {
        val date = LocalDate.now()
        coEvery { local.setLastSyncDate(date) } throws RuntimeException("boom")

        val result = repo.setLastSyncDate(date)

        val pkError = assertIs<PkError>(result)
        assertTrue(pkError.pkError is UnknownError)
    }
}