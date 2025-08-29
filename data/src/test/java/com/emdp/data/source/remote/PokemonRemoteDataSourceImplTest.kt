package com.emdp.data.source.remote

import com.emdp.data.source.remote.api.PokeApiService
import com.emdp.data.source.remote.dtos.PokemonDetailResponseDto
import com.emdp.data.source.remote.dtos.PokemonDetailResponseDtoMother
import com.emdp.data.source.remote.dtos.PokemonListResponseDto
import com.emdp.data.source.remote.dtos.PokemonListResponseDtoMother
import com.emdp.data.source.remote.mapper.PokemonRemoteMapper
import com.emdp.domain.common.base.result.PokedexResult
import com.emdp.domain.common.base.result.PokedexResult.PkSuccess
import com.emdp.domain.model.PokemonDetailModelMother
import com.emdp.domain.model.PokemonListModelMother
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
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
            val pokemonListDto = PokemonListResponseDtoMother.mock()
            val pokemonList = PokemonListModelMother.mock()

            coEvery { api.getPokemonList(offset = 0, limit = ALL_LIMIT) } returns pokemonListDto
            coEvery { mapper.toModel(any<PokemonListResponseDto>()) } returns pokemonList

            val result = dataSource.getAllPokemon()

            assertTrue(result is PkSuccess)
            val pokemonListResult = (result as PkSuccess).pkData
            assertEquals(pokemonList, pokemonListResult)
            assertEquals(pokemonList.size, pokemonListResult.size)

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
        coVerify(exactly = 0) { mapper.toModel(any<PokemonListResponseDto>()) }
        confirmVerified(api, mapper)
    }

    @Test
    fun `getPokemonDetail returns success when api and mapper succeed`() =
        runTest {
            val dto = PokemonDetailResponseDtoMother.mock()
            val model = PokemonDetailModelMother.mock()

            coEvery { api.getPokemonDetail(25) } returns dto
            every { mapper.toModel(dto) } returns model

            val result = dataSource.getPokemonDetail(25)

            assertTrue(result is PkSuccess)
            assertEquals(model, (result as PkSuccess).pkData)
            coVerify(exactly = 1) { api.getPokemonDetail(25) }
            verify(exactly = 1) { mapper.toModel(dto) }
        }

    @Test
    fun `getPokemonDetail returns error when api throws`() = runTest {
        coEvery { api.getPokemonDetail(99999) } throws IOException("network boom")

        val result = dataSource.getPokemonDetail(99999)

        assertTrue(result is PokedexResult.PkError)
        coVerify(exactly = 1) { api.getPokemonDetail(99999) }
        verify(exactly = 0) { mapper.toModel(any<PokemonDetailResponseDto>()) }
    }

    private companion object {
        const val ALL_LIMIT = 100_000
    }
}