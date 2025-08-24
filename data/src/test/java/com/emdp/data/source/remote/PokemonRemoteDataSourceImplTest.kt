package com.emdp.data.source.remote

import com.emdp.data.source.remote.api.PokeApiService
import com.emdp.data.source.remote.dtos.PokemonListResponseDto
import com.emdp.data.source.remote.dtos.PokemonListResultDto
import com.emdp.data.source.remote.mapper.PokemonRemoteMapper
import com.emdp.domain.common.base.result.PokedexResult
import com.emdp.domain.common.base.result.PokedexResult.PkSuccess
import com.emdp.domain.model.PokemonListModel
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import java.io.IOException

internal class PokemonRemoteDataSourceImplTest {

    private lateinit var dataSource: PokemonRemoteDataSource

    private var api: PokeApiService = mockk()
    private var mapper: PokemonRemoteMapper = mockk()

    @Before
    fun setUp() {
        dataSource = PokemonRemoteDataSourceImpl(api, mapper)
    }

    @Test
    fun `getAllPokemon returns Success and hits expected query params`() =
        runTest {
            coEvery { api.getPokemonList(offset = 0, limit = ALL_LIMIT) } returns pokemonListDto
            coEvery { mapper.toModel(any()) } returns pokemonList

            val result = dataSource.getAllPokemon()

            assertTrue(result is PkSuccess)
            val pokemonListResult = (result as PkSuccess).pkData
            assertEquals(pokemonList, pokemonListResult)
            assertEquals(EXPECTED_COUNT, pokemonListResult.size)

            coVerify(exactly = 1) { api.getPokemonList(offset = 0, limit = ALL_LIMIT) }
            coVerify(exactly = 1) { mapper.toModel(pokemonListDto) }
            confirmVerified(api, mapper)
        }

    @Test
    fun `getAllPokemon returns Error on server failure`() = runTest {
        coEvery { api.getPokemonList(offset = 0, limit = ALL_LIMIT) } throws IOException("boom")

        val result = dataSource.getAllPokemon()

        assertTrue(result is PokedexResult.PkError)
        coVerify(exactly = 1) { api.getPokemonList(offset = 0, limit = ALL_LIMIT) }
        coVerify(exactly = 0) { mapper.toModel(any()) }
        confirmVerified(api, mapper)
    }

    private companion object {
        const val ALL_LIMIT = 100_000
        const val EXPECTED_COUNT = 2

        val pokemonListDto = PokemonListResponseDto(
            count = EXPECTED_COUNT,
            next = null,
            previous = null,
            results = listOf(
                PokemonListResultDto(
                    name = "bulbasaur",
                    url = "https://pokeapi.co/api/v2/pokemon/1/"
                ),
                PokemonListResultDto(
                    name = "ivysaur",
                    url = "https://pokeapi.co/api/v2/pokemon/2"
                )
            )
        )

        val pokemonList = listOf(
            PokemonListModel(id = 1, name = "bulbasaur"),
            PokemonListModel(id = 2, name = "ivysaur")
        )
    }
}