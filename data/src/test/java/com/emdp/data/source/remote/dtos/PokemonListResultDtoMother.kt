package com.emdp.data.source.remote.dtos

internal object PokemonListResultDtoMother {

    private const val NAME_1 = "bulbasaur"
    private const val NAME_2 = "ivysaur"
    private const val NAME_BAD = "whoops"

    private const val URL_1_TRAILING_SLASH = "https://pokeapi.co/api/v2/pokemon/1/"
    private const val URL_2_NO_TRAILING_SLASH = "https://pokeapi.co/api/v2/pokemon/2"
    private const val BAD_URL = "https://pokeapi.co/api/v2/not-pokemon/xxx"

    private val base = PokemonListResultDto(
        name = NAME_1,
        url = URL_1_TRAILING_SLASH
    )

    fun mock() = listOf(
        base,
        base.copy(
            name = NAME_2,
            url = URL_2_NO_TRAILING_SLASH
        )
    )

    fun mockEmptyList() = emptyList<PokemonListResultDto>()

    fun mockWithInvalidPattern() = listOf(
        base.copy(
            name = NAME_BAD,
            url = BAD_URL
        )
    )
}