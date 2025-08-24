package com.emdp.data.repository

import com.emdp.data.source.remote.PokemonRemoteDataSource
import com.emdp.domain.common.base.result.PokedexResult.PkError
import com.emdp.domain.common.base.result.PokedexResult.PkSuccess
import com.emdp.domain.model.PokemonListModel
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

@OptIn(ExperimentalCoroutinesApi::class)
internal class PokedexRepositoryImplTest {

    private lateinit var repository: PokedexRepositoryImpl

    private val dataSource: PokemonRemoteDataSource = mockk()

    @Before
    fun setUp() {
        repository = PokedexRepositoryImpl(dataSource)
    }

    @Test
    fun `syncPokemonList returns Success when dataSource succeeds`() = runTest {
        coEvery { dataSource.getAllPokemon() } returns PkSuccess(
            listOf(PokemonListModel(1, "bulbasaur"))
        )

        val result = repository.syncPokemonList()

        assertTrue(result is PkSuccess)
        assertEquals(Unit, (result as PkSuccess).pkData)
        coVerify(exactly = 1) { dataSource.getAllPokemon() }
        confirmVerified(dataSource)
    }

    @Test
    fun `syncPokemonList still returns Success when dataSource fails (temporary behavior)`() =
        runTest {
            coEvery { dataSource.getAllPokemon() } returns PkError(pkError = mockk(relaxed = true))

            val result = repository.syncPokemonList()

            assertTrue(result is PkSuccess)
            coVerify(exactly = 1) { dataSource.getAllPokemon() }
            confirmVerified(dataSource)
        }
}