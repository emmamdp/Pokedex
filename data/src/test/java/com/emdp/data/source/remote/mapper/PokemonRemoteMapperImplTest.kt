package com.emdp.data.source.remote.mapper

import com.emdp.data.source.remote.dtos.PokemonDetailResponseDtoMother
import com.emdp.data.source.remote.dtos.PokemonListResponseDtoMother
import com.emdp.domain.model.PokemonDetailModelMother
import com.emdp.domain.model.PokemonListModel
import com.emdp.domain.model.PokemonListModelMother
import com.emdp.domain.model.types.PokemonType
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Assert.assertThrows
import org.junit.Test

internal class PokemonRemoteMapperImplTest {

    private val mapper = PokemonRemoteMapperImpl()

    @Test
    fun `toModel pokemon list maps list correctly with and without trailing slash`() {
        val dto = PokemonListResponseDtoMother.mock()

        val models = mapper.toModel(responseDto = dto)

        assertEquals(PokemonListModelMother.mock(), models)
    }

    @Test
    fun `toModel pokemon list returns empty list when results is empty`() {
        val dto = PokemonListResponseDtoMother.mockEmptyResult()

        val models = mapper.toModel(responseDto = dto)

        assertEquals(emptyList<PokemonListModel>(), models)
    }

    @Test
    fun `toModel pokemon list throws when url does not match id pattern`() {
        val dto = PokemonListResponseDtoMother.mockWithInvalidPattern()

        assertThrows(IllegalStateException::class.java) {
            mapper.toModel(dto)
        }
    }

    @Test
    fun `toModel pokemon detail capitalizes name, sorts types by slot, and maps stats`() {
        val dto = PokemonDetailResponseDtoMother.mock()

        val model = mapper.toModel(dto)

        with(PokemonDetailModelMother.mock()) {
            assertEquals(id, model.id)
            assertEquals(name, model.name)
            assertEquals(types, model.types)
            assertEquals(imageUrl, model.imageUrl)
            assertEquals(stats.size, model.stats.size)
            stats.forEachIndexed { index, statModel ->
                assertEquals(statModel.name, model.stats[index].name)
                assertEquals(statModel.base, model.stats[index].base)
            }
            assertEquals(7, model.height)
            assertEquals(69, model.weight)
        }
    }

    @Test
    fun `toModel pokemon detail handles nulls and empty lists`() {
        val dto = PokemonDetailResponseDtoMother.mockEmptyDetail()

        val model = mapper.toModel(dto)

        assertEquals(0, model.id)
        assertEquals("", model.name)
        assertNull(model.imageUrl)
        assertEquals(emptyList<PokemonType>(), model.types)
        assertEquals(0, model.stats.size)
        assertNull(model.height)
        assertNull(model.weight)
    }
}