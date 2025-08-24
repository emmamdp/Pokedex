package com.emdp.data.source.local.database.mapper

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import androidx.paging.PagingSource.LoadParams.Refresh
import androidx.paging.PagingState
import androidx.paging.testing.asSnapshot
import com.emdp.data.source.local.database.entities.PokemonListEntity
import com.emdp.domain.model.PokemonListModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test
import kotlin.test.assertSame

@OptIn(ExperimentalCoroutinesApi::class)
internal class PokemonLocalMapperImplTest {

    private val mapper = PokemonLocalMapperImpl()

    @Test
    fun `toEntity maps models to entities`() = runTest {
        val result = mapper.toEntity(MODELS)
        assertEquals(ENTITIES, result)
    }

    @Test
    fun `toEntity returns empty list when input is empty`() = runTest {
        val result = mapper.toEntity(emptyList())
        assertEquals(emptyList<PokemonListEntity>(), result)
    }

    @Test
    fun `toModel maps paging source of entities to models (snapshot)`() = runTest {
        val entitySource = FakeEntityPagingSource(ENTITIES)

        val modelSource: PagingSource<Int, PokemonListModel> = mapper.toModel(entitySource)
        val snapshot = Pager(
            config = PagingConfig(pageSize = 2, initialLoadSize = 4, enablePlaceholders = false),
            pagingSourceFactory = { modelSource }
        ).flow.asSnapshot()

        assertEquals(MODELS, snapshot)
    }

    @Test
    fun `toModel propagates load error from original PagingSource`() = runTest {
        val boom = RuntimeException("boom")
        val errorSource = ErrorEntityPagingSource(boom)

        val modelSource = mapper.toModel(errorSource)
        val res = modelSource.load(
            Refresh(
                key = null,
                loadSize = 10,
                placeholdersEnabled = false
            )
        )

        assertTrue(res is PagingSource.LoadResult.Error)
        assertSame(boom, (res as PagingSource.LoadResult.Error).throwable)
    }

    @Test
    fun `toModel propagates invalidation from original PagingSource`() = runTest {
        val entitySource = FakeEntityPagingSource(ENTITIES)
        val modelSource = mapper.toModel(entitySource)

        assertFalse(modelSource.invalid)
        entitySource.invalidate()
        assertTrue(modelSource.invalid)
    }

    private class FakeEntityPagingSource(
        private val data: List<PokemonListEntity>
    ) : PagingSource<Int, PokemonListEntity>() {

        override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PokemonListEntity> {
            val start = params.key ?: 0
            if (start < 0 || start > data.size) return LoadResult.Page(emptyList(), null, null)

            val end = (start + params.loadSize).coerceAtMost(data.size)
            val page = data.subList(start, end)

            val prev = if (start > 0) (start - params.loadSize).coerceAtLeast(0) else null
            val next = if (end < data.size) end else null

            return LoadResult.Page(
                data = page,
                prevKey = prev,
                nextKey = next,
                itemsBefore = start,
                itemsAfter = (data.size - end)
            )
        }

        override fun getRefreshKey(state: PagingState<Int, PokemonListEntity>): Int? = 0
    }

    private class ErrorEntityPagingSource(
        private val error: Throwable
    ) : PagingSource<Int, PokemonListEntity>() {
        override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PokemonListEntity> =
            LoadResult.Error(error)

        override fun getRefreshKey(state: PagingState<Int, PokemonListEntity>): Int? = null
    }

    private companion object {
        val MODELS = listOf(
            PokemonListModel(1, "bulbasaur"),
            PokemonListModel(2, "ivysaur"),
            PokemonListModel(3, "venusaur")
        )
        val ENTITIES = listOf(
            PokemonListEntity(1, "bulbasaur"),
            PokemonListEntity(2, "ivysaur"),
            PokemonListEntity(3, "venusaur")
        )
    }
}