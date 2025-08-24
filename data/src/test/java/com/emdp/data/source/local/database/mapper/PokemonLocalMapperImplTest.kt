package com.emdp.data.source.local.database.mapper

import com.emdp.data.source.local.database.entities.PokemonListEntity
import com.emdp.domain.model.PokemonListModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
internal class PokemonLocalMapperImplTest {

    private val mapper = PokemonLocalMapperImpl()

    @Test
    fun `toEntity maps domain models to entities`() = runTest {
        val result = mapper.toEntity(MODELS)
        assertEquals(ENTITIES, result)
    }

    @Test
    fun `toEntity returns empty list when input is empty`() = runTest {
        val result = mapper.toEntity(emptyList())
        assertEquals(emptyList<PokemonListEntity>(), result)
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