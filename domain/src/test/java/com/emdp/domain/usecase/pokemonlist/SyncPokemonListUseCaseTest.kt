package com.emdp.domain.usecase.pokemonlist

import com.emdp.domain.common.base.result.PokedexResult.PkError
import com.emdp.domain.common.base.result.PokedexResult.PkSuccess
import com.emdp.domain.model.base.NoParams
import com.emdp.domain.repository.PokedexRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

internal class SyncPokemonListUseCaseImplTest {

    private lateinit var useCase: SyncPokemonListUseCaseImpl

    private val repository: PokedexRepository = mockk()

    @Before
    fun setUp() {
        useCase = SyncPokemonListUseCaseImpl(repository)
    }

    @Test
    fun `invoke returns Success when repository succeeds`() = runTest {
        coEvery { repository.syncPokemonList() } returns PkSuccess(Unit)

        val result = useCase.invoke(NoParams)

        assertTrue(result is PkSuccess<*>)
        val data = (result as PkSuccess).pkData
        assertEquals(Unit, data)
        coVerify(exactly = 1) { repository.syncPokemonList() }
        confirmVerified(repository)
    }

    @Test
    fun `invoke returns Error when repository fails`() = runTest {
        coEvery { repository.syncPokemonList() } returns PkError(pkError = mockk(relaxed = true))

        val result = useCase.invoke(NoParams)

        assertTrue(result is PkError)
        coVerify(exactly = 1) { repository.syncPokemonList() }
        confirmVerified(repository)
    }
}