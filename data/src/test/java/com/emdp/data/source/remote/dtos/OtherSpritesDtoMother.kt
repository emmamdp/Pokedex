package com.emdp.data.source.remote.dtos

internal object OtherSpritesDtoMother {

    private val base = OtherSpritesDto(
        dreamWorld = DreamWorldDtoMother.mock(),
        officialArtwork = OfficialArtworkDtoMother.mock()
    )

    fun mock() = base

    fun mockWithOfficialArtWorkNull() = base.copy(
        officialArtwork = OfficialArtworkDtoMother.mockNull()
    )
}