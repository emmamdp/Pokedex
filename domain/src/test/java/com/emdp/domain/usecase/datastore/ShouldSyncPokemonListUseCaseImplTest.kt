package com.emdp.domain.usecase.datastore

import com.emdp.domain.common.base.result.PokedexResult.PkError
import com.emdp.domain.common.base.result.PokedexResult.PkSuccess
import com.emdp.domain.model.base.NoParams
import com.emdp.domain.repository.SyncPokedexRepository
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import java.time.LocalDate
import kotlin.test.assertEquals
import kotlin.test.assertTrue

internal class ShouldSyncPokemonListUseCaseImplTest {

    private lateinit var useCase: ShouldSyncPokemonListUseCase

    private var repository: SyncPokedexRepository = mockk()

    @Before
    fun setUp() {
        useCase = ShouldSyncPokemonListUseCaseImpl(repository)
    }

    @After
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun `returns true when never synced before`() = runTest {
        coEvery { repository.getLastSyncDate() } returns PkSuccess(null)

        val result = useCase.invoke(NoParams)

        assertTrue(result is PkSuccess)
        assertEquals(true, result.pkData)
        coVerify(exactly = 1) { repository.getLastSyncDate() }
        confirmVerified(repository)
    }

    @Test
    fun `returns false when already synced once (any past date)`() = runTest {
        coEvery { repository.getLastSyncDate() } returns PkSuccess(SOME_PAST_DATE)

        val result = useCase.invoke(NoParams)

        assertTrue(result is PkSuccess)
        assertEquals(false, result.pkData)
        coVerify(exactly = 1) { repository.getLastSyncDate() }
        confirmVerified(repository)
    }

    @Test
    fun `propagates error when repository fails`() = runTest {
        coEvery { repository.getLastSyncDate() } returns PkError(pkError = mockk(relaxed = true))

        val result = useCase.invoke(NoParams)

        assertTrue(result is PkError)
        coVerify(exactly = 1) { repository.getLastSyncDate() }
        confirmVerified(repository)
    }

    private companion object {
        val SOME_PAST_DATE: LocalDate = LocalDate.of(2025, 8, 1)
    }
}