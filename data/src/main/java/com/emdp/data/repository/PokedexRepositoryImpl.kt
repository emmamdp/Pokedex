package com.emdp.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.emdp.data.common.base.safeCall
import com.emdp.data.source.local.database.PokemonLocalDataSource
import com.emdp.data.source.remote.PokemonRemoteDataSource
import com.emdp.domain.common.base.result.PokedexResult
import com.emdp.domain.common.base.result.PokedexResult.PkError
import com.emdp.domain.common.base.result.PokedexResult.PkSuccess
import com.emdp.domain.model.PokemonListModel
import com.emdp.domain.repository.PokedexRepository
import kotlinx.coroutines.flow.Flow

class PokedexRepositoryImpl(
    private val remote: PokemonRemoteDataSource,
    private val local: PokemonLocalDataSource
) : PokedexRepository {

    override suspend fun syncPokemonList(): PokedexResult<Unit> =
        when (val result = remote.getAllPokemon()) {
            is PkSuccess -> insertAllPokemonInDatabase(result.pkData)
            is PkError -> result
        }

    override fun getPagedPokemonList(): Flow<PagingData<PokemonListModel>> =
        Pager(
            config = PagingConfig(
                pageSize = DEFAULT_PAGE_SIZE,
                prefetchDistance = DEFAULT_PAGE_SIZE / 2,
                initialLoadSize = DEFAULT_PAGE_SIZE * 2,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { local.pagingSource() }
        ).flow

    private suspend fun insertAllPokemonInDatabase(modelList: List<PokemonListModel>) =
        when (val insertResult = safeCall { local.insertAll(modelList) }) {
            is PkSuccess -> PkSuccess(Unit)
            is PkError -> insertResult
        }

    private companion object {
        const val DEFAULT_PAGE_SIZE = 50
    }
}