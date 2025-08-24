package com.emdp.data.source.local.database

import com.emdp.domain.model.PokemonListModel

interface PokemonLocalDataSource {
    suspend fun insertAll(items: List<PokemonListModel>)
}