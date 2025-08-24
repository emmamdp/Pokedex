package com.emdp.data.repository

import android.database.SQLException
import com.emdp.data.source.local.database.PokemonLocalDataSource
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

    private val remote: PokemonRemoteDataSource = mockk()
    private val local: PokemonLocalDataSource = mockk()

    @Before
    fun setUp() {
        repository = PokedexRepositoryImpl(remote, local)
    }

    @Test
    fun `syncPokemonList returns Success and writes to local`() =
        runTest {
            coEvery { remote.getAllPokemon() } returns PkSuccess(POKEMON_LIST)
            coEvery { local.insertAll(POKEMON_LIST) } returns Unit

            val result = repository.syncPokemonList()

            assertTrue(result is PkSuccess)
            assertEquals(Unit, (result as PkSuccess).pkData)
            coVerify(exactly = 1) { remote.getAllPokemon() }
            coVerify(exactly = 1) { local.insertAll(POKEMON_LIST) }
            confirmVerified(remote, local)
        }

    @Test
    fun `syncPokemonList returns Error when remote fails and does not touch local`() =
        runTest {
            coEvery { remote.getAllPokemon() } returns PkError(pkError = mockk(relaxed = true))

            val result = repository.syncPokemonList()

            assertTrue(result is PkError)
            coVerify(exactly = 1) { remote.getAllPokemon() }
            coVerify(exactly = 0) { local.insertAll(any()) }
            confirmVerified(remote, local)
        }

    @Test
    fun `syncPokemonList returns Error when local insert fails (safeCall maps it)`() =
        runTest {
            coEvery { remote.getAllPokemon() } returns PkSuccess(POKEMON_LIST)
            coEvery { local.insertAll(POKEMON_LIST) } throws SQLException("db boom")

            val result = repository.syncPokemonList()

            assertTrue(result is PkError)
            coVerify(exactly = 1) { remote.getAllPokemon() }
            coVerify(exactly = 1) { local.insertAll(POKEMON_LIST) }
            confirmVerified(remote, local)
        }

    private companion object {
        val POKEMON_LIST = listOf(
            PokemonListModel(id = 1, name = "bulbasaur"),
            PokemonListModel(id = 2, name = "ivysaur")
        )
    }
}