package com.emdp.data.source.remote.dtos

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PokemonDetailResponseDto(
    val id: Int,
    val name: String,
    val height: Int?,
    val weight: Int?,
    val sprites: SpritesDto?,
    val types: List<TypeSlotDto>?,
    val stats: List<StatDto>?
)

@JsonClass(generateAdapter = true)
data class SpritesDto(
    @Json(name = "front_default") val frontDefault: String?
)

@JsonClass(generateAdapter = true)
data class TypeSlotDto(
    val slot: Int,
    val type: NamedResourceDto
)

@JsonClass(generateAdapter = true)
data class NamedResourceDto(
    val name: String,
    val url: String
)

@JsonClass(generateAdapter = true)
data class StatDto(
    @Json(name = "base_stat") val baseStat: Int,
    val stat: NamedResourceDto
)