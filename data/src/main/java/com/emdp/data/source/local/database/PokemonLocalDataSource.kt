package com.emdp.data.source.local.database

import androidx.paging.PagingSource
import com.emdp.domain.model.PokemonListModel

interface PokemonLocalDataSource {
    suspend fun insertAll(items: List<PokemonListModel>)
    fun pagingSource(): PagingSource<Int, PokemonListModel>
}