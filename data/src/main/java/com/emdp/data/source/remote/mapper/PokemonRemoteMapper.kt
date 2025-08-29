package com.emdp.data.source.remote.mapper

import com.emdp.data.source.remote.dtos.PokemonDetailResponseDto
import com.emdp.data.source.remote.dtos.PokemonListResponseDto
import com.emdp.domain.model.PokemonDetailModel
import com.emdp.domain.model.PokemonListModel

interface PokemonRemoteMapper {
    fun toModel(responseDto: PokemonListResponseDto): List<PokemonListModel>
    fun toModel(responseDto: PokemonDetailResponseDto): PokemonDetailModel
}