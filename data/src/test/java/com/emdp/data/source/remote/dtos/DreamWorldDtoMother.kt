package com.emdp.data.source.remote.dtos

internal object DreamWorldDtoMother {

    private val base = DreamWorldDto(frontDefault = "https://sprites/dream_world.svg")

    fun mock() = base
}