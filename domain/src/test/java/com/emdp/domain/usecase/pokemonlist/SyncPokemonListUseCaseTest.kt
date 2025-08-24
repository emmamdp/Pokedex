package com.emdp.domain.usecase.pokemonlist

import com.emdp.domain.common.base.result.PokedexResult.PkError
import com.emdp.domain.common.base.result.PokedexResult.PkSuccess
import com.emdp.domain.model.base.NoParams
import com.emdp.domain.repository.PokedexRepository
import com.emdp.domain.repository.SyncPokedexRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import java.time.LocalDate

@OptIn(ExperimentalCoroutinesApi::class)
internal class SyncPokemonListUseCaseImplTest {

    private lateinit var useCase: SyncPokemonListUseCaseImpl

    private val pokedexRepository: PokedexRepository = mockk()
    private val syncPokedexRepository: SyncPokedexRepository = mockk()

    @Before
    fun setUp() {
        useCase = SyncPokemonListUseCaseImpl(
            repository = pokedexRepository,
            syncPokedexRepository = syncPokedexRepository
        )
    }

    @Test
    fun `returns Success and stores today when repository sync succeeds`() =
        runTest {
            coEvery { pokedexRepository.syncPokemonList() } returns PkSuccess(Unit)
            coEvery { syncPokedexRepository.setLastSyncDate(any()) } returns PkSuccess(Unit)

            val today = LocalDate.now()

            val result = useCase.invoke(NoParams)

            assertTrue(result is PkSuccess)
            assertEquals(Unit, (result as PkSuccess).pkData)
            coVerify(exactly = 1) { pokedexRepository.syncPokemonList() }
            coVerify(exactly = 1) { syncPokedexRepository.setLastSyncDate(match { it == today }) }
            confirmVerified(pokedexRepository, syncPokedexRepository)
        }

    @Test
    fun `returns Error when storing last sync date fails`() = runTest {
        coEvery { pokedexRepository.syncPokemonList() } returns PkSuccess(Unit)
        coEvery { syncPokedexRepository.setLastSyncDate(any()) } returns PkError(
            pkError = mockk(relaxed = true)
        )

        val result = useCase.invoke(NoParams)

        assertTrue(result is PkError)
        coVerify(exactly = 1) { pokedexRepository.syncPokemonList() }
        coVerify(exactly = 1) { syncPokedexRepository.setLastSyncDate(any()) }
        confirmVerified(pokedexRepository, syncPokedexRepository)
    }

    @Test
    fun `returns Error and does not store date when repository sync fails`() = runTest {
        coEvery { pokedexRepository.syncPokemonList() } returns PkError(pkError = mockk(relaxed = true))

        val result = useCase.invoke(NoParams)

        assertTrue(result is PkError)
        coVerify(exactly = 1) { pokedexRepository.syncPokemonList() }
        coVerify(exactly = 0) { syncPokedexRepository.setLastSyncDate(any()) }
        confirmVerified(pokedexRepository, syncPokedexRepository)
    }
}