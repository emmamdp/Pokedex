package com.emdp.data.source.local.database.mapper

import com.emdp.data.source.local.database.entities.PokemonListEntity
import com.emdp.domain.model.PokemonListModel

interface PokemonLocalMapper {
    suspend fun toEntity(model: List<PokemonListModel>): List<PokemonListEntity>
}