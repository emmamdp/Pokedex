package com.emdp.data.source.local.database

import com.emdp.data.source.local.database.dao.PokemonListDao
import com.emdp.data.source.local.database.entities.PokemonListEntity
import com.emdp.data.source.local.database.mapper.PokemonLocalMapper
import com.emdp.domain.model.PokemonListModel
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import kotlin.test.assertFailsWith

@OptIn(ExperimentalCoroutinesApi::class)
internal class PokemonLocalDataSourceImplTest {

    private lateinit var dataSource: PokemonLocalDataSourceImpl

    private val dao: PokemonListDao = mockk()
    private val mapper: PokemonLocalMapper = mockk()

    @Before
    fun setUp() {
        dataSource = PokemonLocalDataSourceImpl(dao, mapper)
    }

    @Test
    fun `insertAll maps models and delegates to dao`() = runTest {
        coEvery { mapper.toEntity(MODELS) } returns ENTITIES
        coEvery { dao.insertAll(ENTITIES) } returns Unit

        dataSource.insertAll(MODELS)

        coVerify(exactly = 1) { mapper.toEntity(MODELS) }
        coVerify(exactly = 1) { dao.insertAll(ENTITIES) }
        confirmVerified(mapper, dao)
    }

    @Test
    fun `insertAll propagates dao exception`() = runTest {
        coEvery { mapper.toEntity(MODELS) } returns ENTITIES
        coEvery { dao.insertAll(ENTITIES) } throws java.sql.SQLException("db boom")

        assertFailsWith<java.sql.SQLException> {
            dataSource.insertAll(MODELS)
        }
        coVerify(exactly = 1) { mapper.toEntity(MODELS) }
        coVerify(exactly = 1) { dao.insertAll(ENTITIES) }
        confirmVerified(mapper, dao)
    }

    private companion object {
        val MODELS = listOf(
            PokemonListModel(id = 1, name = "bulbasaur"),
            PokemonListModel(id = 2, name = "ivysaur")
        )
        val ENTITIES = listOf(
            PokemonListEntity(id = 1, name = "bulbasaur"),
            PokemonListEntity(id = 2, name = "ivysaur")
        )
    }
}