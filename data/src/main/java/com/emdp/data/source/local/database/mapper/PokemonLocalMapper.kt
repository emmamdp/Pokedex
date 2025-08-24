package com.emdp.data.source.local.database.mapper

import androidx.paging.PagingSource
import com.emdp.data.source.local.database.entities.PokemonListEntity
import com.emdp.domain.model.PokemonListModel

interface PokemonLocalMapper {
    suspend fun toEntity(model: List<PokemonListModel>): List<PokemonListEntity>
    fun toModel(entity: PagingSource<Int, PokemonListEntity>): PagingSource<Int, PokemonListModel>
}