package com.emdp.data.source.remote.dtos

internal object PokemonDetailResponseDtoMother {

    private val base = PokemonDetailResponseDto(
        id = 1,
        name = "bulbasaur",
        height = 7,
        weight = 69,
        sprites = SpritesDtoMother.mock(),
        types = TypeSlotDtoMother.mock(),
        stats = StatDtoMother.mock()
    )

    fun mock() = base

    fun mockEmptyDetail() = base.copy(
        id = 0,
        name = "",
        height = null,
        weight = null,
        sprites = null,
        types = null,
        stats = null
    )

    fun mockWithOfficialArtWorkNull() = base.copy(
        sprites = SpritesDtoMother.mockWithOfficialArtWorkNull()
    )

    fun mockWithOtherNull() = base.copy(
        sprites = SpritesDtoMother.mockOtherNull()
    )

}