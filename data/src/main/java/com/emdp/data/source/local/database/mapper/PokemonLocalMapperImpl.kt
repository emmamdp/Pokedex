package com.emdp.data.source.local.database.mapper

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.emdp.data.source.local.database.entities.PokemonListEntity
import com.emdp.domain.model.PokemonListModel
import kotlin.collections.map

class PokemonLocalMapperImpl: PokemonLocalMapper {

    override suspend fun toEntity(model: List<PokemonListModel>): List<PokemonListEntity> =
        model.map { PokemonListEntity(id = it.id, name = it.name) }

    override fun toModel(entity: PagingSource<Int, PokemonListEntity>): PagingSource<Int, PokemonListModel> =
        entity.mapPagingSource { source -> PokemonListModel(id = source.id, name = source.name) }

    private fun <K : Any, From : Any, To : Any> PagingSource<K, From>.mapPagingSource(
        transform: (From) -> To
    ): PagingSource<K, To> = object : PagingSource<K, To>() {

        init {
            this@mapPagingSource.registerInvalidatedCallback { invalidate() }
        }

        override suspend fun load(params: LoadParams<K>): LoadResult<K, To> {
            return when (val res = this@mapPagingSource.load(params)) {
                is LoadResult.Page -> LoadResult.Page(
                    data = res.data.map(transform),
                    prevKey = res.prevKey,
                    nextKey = res.nextKey,
                    itemsBefore = res.itemsBefore,
                    itemsAfter = res.itemsAfter
                )
                is LoadResult.Error -> LoadResult.Error(res.throwable)
                is LoadResult.Invalid -> LoadResult.Invalid()
            }
        }

        override fun getRefreshKey(state: PagingState<K, To>): K? = null

        override val jumpingSupported: Boolean get() = this@mapPagingSource.jumpingSupported
        override val keyReuseSupported: Boolean get() = this@mapPagingSource.keyReuseSupported
    }
}