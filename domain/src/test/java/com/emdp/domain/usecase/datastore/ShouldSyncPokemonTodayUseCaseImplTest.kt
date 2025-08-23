package com.emdp.domain.usecase.datastore

import com.emdp.domain.common.base.result.PokedexResult.Error
import com.emdp.domain.common.base.result.PokedexResult.Success
import com.emdp.domain.model.base.NoParams
import com.emdp.domain.model.error.PokedexGenericError.NoConnection
import com.emdp.domain.repository.SyncPokedexRepository
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import java.time.LocalDate
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

internal class ShouldSyncPokemonTodayUseCaseImplTest {

    private var repository: SyncPokedexRepository = mockk()
    private lateinit var useCase: ShouldSyncPokemonTodayUseCase

    @Before
    fun setUp() {
        useCase = ShouldSyncPokemonTodayUseCaseImpl(repository)
    }

    @After
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun `returns true when last sync is null`() = runTest {
        coEvery { repository.getLastSyncDate() } returns Success(null)

        val result = useCase(NoParams)

        if (result is Success)
            assertTrue(result.pkData)
    }

    @Test
    fun `returns false when last sync is today`() = runTest {
        val today = LocalDate.now()
        coEvery { repository.getLastSyncDate() } returns Success(today)

        val result = useCase(NoParams)

        if (result is Success)
            assertFalse(result.pkData)
    }

    @Test
    fun `returns true when last sync is before today`() = runTest {
        val yesterday = LocalDate.now().minusDays(1)
        coEvery { repository.getLastSyncDate() } returns Success(yesterday)

        val result = useCase.invoke(NoParams)

        if (result is Success)
            assertTrue(result.pkData)
    }

    @Test
    fun `propagates error from repository`() = runTest {
        coEvery { repository.getLastSyncDate() } returns Error(NoConnection)

        val result = useCase.invoke(NoParams)

        if (result is Error)
            assertEquals(NoConnection, result.pkError)
    }
}