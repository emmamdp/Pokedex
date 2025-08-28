package com.emdp.features.pokemon_list.presentation

import androidx.paging.PagingData
import com.emdp.core.common.base.presentation.PokedexBaseState
import com.emdp.core.navigation.PokedexDestination
import com.emdp.domain.model.PokemonListModel
import com.emdp.domain.usecase.pokemonlist.GetPokemonListUseCase
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
internal class PokemonListViewModelTest {

    private val getPokemonListUseCase: GetPokemonListUseCase = mockk(relaxed = false)

    @Test
    fun `initial state is Content(Unit)`() = runTest {
        every { getPokemonListUseCase() } returns flowOf(PagingData.empty())

        val vm = PokemonListViewModel(getPokemonListUseCase)

        val state = vm.screenState.value
        assertTrue(state is PokedexBaseState.Content)
        assertEquals(Unit, (state as PokedexBaseState.Content).data)

        verify(exactly = 1) { getPokemonListUseCase() }
    }

    @Test
    fun `pokemons flow emits snapshot from use case`() = runTest {
        val expected = listOf(
            PokemonListModel(1, "bulbasaur"),
            PokemonListModel(2, "ivysaur"),
            PokemonListModel(3, "venusaur")
        )
        every { getPokemonListUseCase() } returns flowOf(PagingData.from(expected))

        PokemonListViewModel(getPokemonListUseCase)

        verify(exactly = 1) { getPokemonListUseCase() }
    }

    @Test
    fun `onPokemonClick triggers navigation to detail`() = runTest {
        every { getPokemonListUseCase() } returns flowOf(PagingData.empty())

        val vm = PokemonListViewModel(getPokemonListUseCase)

        val targetId = 25
        vm.onPokemonClick(targetId)

        val state = vm.screenState.value
        assertTrue(state is PokedexBaseState.NavigateToNextView)
        assertEquals(
            PokedexDestination.OpenPokemonDetail(targetId),
            (state as PokedexBaseState.NavigateToNextView).destination
        )
        verify(exactly = 1) { getPokemonListUseCase() }
    }

    @Test
    fun `onToggleFavorite adds and removes id in favorites set`() = runTest {
        every { getPokemonListUseCase() } returns flowOf(PagingData.empty())

        val vm = PokemonListViewModel(getPokemonListUseCase)

        val id = 7
        vm.onToggleFavorite(id)
        assertTrue(vm.favorites.value.contains(id))
        vm.onToggleFavorite(id)
        assertFalse(vm.favorites.value.contains(id))
    }
}