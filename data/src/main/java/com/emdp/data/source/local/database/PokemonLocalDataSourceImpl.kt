package com.emdp.data.source.local.database

import androidx.paging.PagingSource
import com.emdp.data.source.local.database.dao.PokemonListDao
import com.emdp.data.source.local.database.mapper.PokemonLocalMapper
import com.emdp.domain.model.PokemonListModel

class PokemonLocalDataSourceImpl(
    private val dao: PokemonListDao,
    private val mapper: PokemonLocalMapper
) : PokemonLocalDataSource {

    override suspend fun insertAll(items: List<PokemonListModel>) =
        mapper.toEntity(items).let { entityList ->
            dao.insertAll(entityList)
        }

    override fun pagingSource(): PagingSource<Int, PokemonListModel> {
        val data = dao.pagingSource()
        return mapper.toModel(data)
    }
}