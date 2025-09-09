package com.emdp.features.pokemon_list.presentation

import androidx.paging.PagingData
import com.emdp.core.common.base.presentation.PokedexBaseState
import com.emdp.core.navigation.PokedexDestination
import com.emdp.domain.model.PokemonListModel
import com.emdp.domain.usecase.pokemonlist.GetPokemonListUseCase
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
internal class PokemonListViewModelTest {

    private val getPokemonListUseCase: GetPokemonListUseCase = mockk()

    private var pokemonList: List<PokemonListModel> = emptyList()

    private lateinit var viewModel: PokemonListViewModel

    @Before
    fun setUp() {
        every { getPokemonListUseCase() } answers { flowOf(PagingData.from(pokemonList)) }
        viewModel = PokemonListViewModel(getPokemonListUseCase = getPokemonListUseCase)
    }

    @Test
    fun `initial state is Loading`() {
        val state = viewModel.screenState.value

        assertTrue(state is PokedexBaseState.Loading)
    }

    @Test
    fun `onPokemonClick navigates to OpenPokemonDetail`() {
        viewModel.onPokemonClick(POKEMON_ID)
        val state = viewModel.screenState.value

        assertEquals(
            PokedexDestination.OpenPokemonDetail(POKEMON_ID),
            (state as PokedexBaseState.NavigateToNextView).destination
        )
    }

    @Test
    fun `onToggleFavorite should update favorites state`() {
        // first toggle adds to favorites
        viewModel.onToggleFavorite(POKEMON_ID)
        var favorites = viewModel.favorites.value

        assertTrue(favorites.contains(POKEMON_ID))

        // second toggle removes from favorites
        viewModel.onToggleFavorite(POKEMON_ID)
        favorites = viewModel.favorites.value

        assertFalse(favorites.contains(POKEMON_ID))
    }

    @Test
    fun `onSearchQueryChange updates searchQuery value`() {
        viewModel.onSearchQueryChange(POKEMON_NAME)
        val searchQuery = viewModel.searchQuery.value

        assertEquals(POKEMON_NAME, searchQuery)
    }

    @Test
    fun `clearSearch updates searchQuery value to empty`() {
        viewModel.clearSearch()
        val searchQuery = viewModel.searchQuery.value

        assertEquals(EMPTY_STRING, searchQuery)
    }

    private companion object {
        const val POKEMON_ID = 25
        const val POKEMON_NAME = "bulbasaur"
        const val EMPTY_STRING = ""
    }
}