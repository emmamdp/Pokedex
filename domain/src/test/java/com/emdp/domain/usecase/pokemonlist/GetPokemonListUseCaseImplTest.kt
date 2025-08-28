package com.emdp.domain.usecase.pokemonlist

import androidx.paging.PagingData
import androidx.paging.testing.asSnapshot
import com.emdp.domain.model.PokemonListModel
import com.emdp.domain.repository.PokedexRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

internal class GetPokemonListUseCaseImplTest {

    private lateinit var useCase: GetPokemonListUseCaseImpl

    private val repository: PokedexRepository = mockk()

    @Before
    fun setUp() {
        useCase = GetPokemonListUseCaseImpl(repository)
    }

    @Test
    fun `returns snapshot from repository`() = runTest {
        val expected = listOf(
            PokemonListModel(1, "bulbasaur"),
            PokemonListModel(2, "ivysaur"),
            PokemonListModel(3, "venusaur")
        )
        every { repository.getPagedPokemonList() } returns flowOf(PagingData.from(expected))

        val snapshot = useCase().asSnapshot()

        assertEquals(expected, snapshot)
        verify(exactly = 1) { repository.getPagedPokemonList() }
    }

    @Test
    fun `returns empty snapshot when repository emits empty`() = runTest {
        every { repository.getPagedPokemonList() } returns flowOf(PagingData.empty())

        val snapshot = useCase().asSnapshot()

        assertEquals(emptyList<PokemonListModel>(), snapshot)
        verify(exactly = 1) { repository.getPagedPokemonList() }
    }
}