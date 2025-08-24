package com.emdp.data.source.remote.mapper

import com.emdp.data.source.remote.dtos.PokemonListResponseDto
import com.emdp.data.source.remote.dtos.PokemonListResultDto
import com.emdp.domain.model.PokemonListModel
import org.junit.Assert.assertEquals
import org.junit.Assert.assertThrows
import org.junit.Test

internal class PokemonRemoteMapperImplTest {

    private val mapper = PokemonRemoteMapperImpl()

    @Test
    fun `maps list correctly with and without trailing slash`() {
        val dto = PokemonListResponseDto(
            count = COUNT_TWO,
            next = null,
            previous = null,
            results = listOf(
                PokemonListResultDto(name = NAME_1, url = URL_1_TRAILING_SLASH),
                PokemonListResultDto(name = NAME_2, url = URL_2_NO_TRAILING_SLASH)
            )
        )

        val models = mapper.toModel(dto)

        assertEquals(
            listOf(
                PokemonListModel(id = ID_1, name = NAME_1),
                PokemonListModel(id = ID_2, name = NAME_2)
            ),
            models
        )
    }

    @Test
    fun `returns empty list when results is empty`() {
        val dto = PokemonListResponseDto(
            count = COUNT_ZERO,
            next = null,
            previous = null,
            results = emptyList()
        )

        val models = mapper.toModel(dto)

        assertEquals(emptyList<PokemonListModel>(), models)
    }

    @Test
    fun `throws when url does not match id pattern`() {
        val dto = PokemonListResponseDto(
            count = COUNT_ONE,
            next = null,
            previous = null,
            results = listOf(
                PokemonListResultDto(name = NAME_BAD, url = BAD_URL)
            )
        )

        assertThrows(IllegalStateException::class.java) {
            mapper.toModel(dto)
        }
    }

    private companion object {
        const val COUNT_ZERO = 0
        const val COUNT_ONE = 1
        const val COUNT_TWO = 2

        const val NAME_1 = "bulbasaur"
        const val NAME_2 = "ivysaur"
        const val NAME_BAD = "whoops"

        const val ID_1 = 1
        const val ID_2 = 2

        const val URL_1_TRAILING_SLASH = "https://pokeapi.co/api/v2/pokemon/1/"
        const val URL_2_NO_TRAILING_SLASH = "https://pokeapi.co/api/v2/pokemon/2"
        const val BAD_URL = "https://pokeapi.co/api/v2/not-pokemon/xxx"
    }
}