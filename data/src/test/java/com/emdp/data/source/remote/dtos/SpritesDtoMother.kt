package com.emdp.data.source.remote.dtos

internal object SpritesDtoMother {

    private val base = SpritesDto(
        frontDefault = "https://img",
        other = OtherSpritesDtoMother.mock()
    )

    fun mock() = base

    fun mockWithOfficialArtWorkNull() = base.copy(
        other = OtherSpritesDtoMother.mockWithOfficialArtWorkNull()
    )

    fun mockOtherNull() = base.copy(
        other = null
    )
}