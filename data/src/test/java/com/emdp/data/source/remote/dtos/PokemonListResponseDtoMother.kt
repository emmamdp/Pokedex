package com.emdp.data.source.remote.dtos

internal object PokemonListResponseDtoMother {

    private const val COUNT_ZERO = 0
    private const val COUNT_ONE = 1
    private const val COUNT_TWO = 2

    private val base = PokemonListResponseDto(
        count = COUNT_TWO,
        next = null,
        previous = null,
        results = PokemonListResultDtoMother.mock()
    )

    fun mock() = base

    fun mockEmptyResult() = base.copy(
        count = COUNT_ZERO,
        results = PokemonListResultDtoMother.mockEmptyList()
    )

    fun mockWithInvalidPattern() = base.copy(
        count = COUNT_ONE,
        results = PokemonListResultDtoMother.mockWithInvalidPattern()
    )
}