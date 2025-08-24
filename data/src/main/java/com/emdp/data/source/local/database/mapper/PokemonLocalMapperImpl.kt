package com.emdp.data.source.local.database.mapper

import com.emdp.data.source.local.database.entities.PokemonListEntity
import com.emdp.domain.model.PokemonListModel

class PokemonLocalMapperImpl: PokemonLocalMapper {

    override suspend fun toEntity(model: List<PokemonListModel>): List<PokemonListEntity> =
        model.map { PokemonListEntity(id = it.id, name = it.name) }
}