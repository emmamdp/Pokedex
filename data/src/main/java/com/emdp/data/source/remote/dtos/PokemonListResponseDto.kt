package com.emdp.data.source.remote.dtos

data class PokemonListResponseDto(
    val count: Int,
    val next: String?,
    val previous: String?,
    val results: List<PokemonListResultDto>?
)

data class PokemonListResultDto(
    val name: String,
    val url: String
)