package com.emdp.data.source.remote.dtos

internal object OfficialArtworkDtoMother {

    private val base = OfficialArtworkDto(frontDefault = "https://sprites/official_art.png")

    fun mock() = base

    fun mockNull() = null
}