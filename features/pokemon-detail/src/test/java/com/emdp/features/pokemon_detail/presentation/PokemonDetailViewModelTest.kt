package com.emdp.features.pokemon_detail.presentation

import com.emdp.core.common.base.presentation.PokedexBaseState
import com.emdp.domain.common.base.result.PokedexResult.PkError
import com.emdp.domain.common.base.result.PokedexResult.PkSuccess
import com.emdp.domain.model.PokemonDetailModelMother
import com.emdp.domain.model.error.PokedexGenericError.NoConnection
import com.emdp.domain.usecase.pokemondetail.GetPokemonDetailUseCase
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestWatcher
import org.junit.runner.Description

@OptIn(ExperimentalCoroutinesApi::class)
internal class PokemonDetailViewModelTest {

    @get:Rule
    val mainRule = MainDispatcherRule()

    private var getPokemonDetailUseCase: GetPokemonDetailUseCase = mockk<GetPokemonDetailUseCase>()

    private lateinit var viewModel: PokemonDetailViewModel

    @Before
    fun setUp() {
        viewModel = PokemonDetailViewModel(getPokemonDetailUseCase)
    }

    @Test
    fun `initial state is Loading`() {
        val state = viewModel.screenState.value

        assertTrue(state is PokedexBaseState.Loading)
    }

    @Test
    fun `load success updates state to Content`() = runTest {
        val pokemonId = 25
        val expected = PokemonDetailModelMother.mock()

        coEvery { getPokemonDetailUseCase.invoke(pokemonId) } returns PkSuccess(expected)

        viewModel.load(pokemonId)

        val state = viewModel.screenState.value
        assertTrue(state is PokedexBaseState.Content)
        assertEquals(expected, (state as PokedexBaseState.Content).data)
        coVerify(exactly = 1) { getPokemonDetailUseCase.invoke(pokemonId) }
    }

    @Test
    fun `load error updates state to ShowScreenError`() = runTest {
        coEvery { getPokemonDetailUseCase(any()) } returns PkError(NoConnection)

        viewModel.load(1)

        val state = viewModel.screenState.value
        assertTrue(state is PokedexBaseState.ShowScreenError)
    }

    @Test
    fun `retry after error calls use case again and updates to Content`() = runTest {
        coEvery { getPokemonDetailUseCase(any()) } returns PkError(NoConnection)

        viewModel.load(1)

        assertTrue(viewModel.screenState.value is PokedexBaseState.ShowScreenError)

        val expected = PokemonDetailModelMother.mockOtherPokemon()
        coEvery { getPokemonDetailUseCase(any()) } returns PkSuccess(expected)

        viewModel.retry()
        advanceUntilIdle()

        val state = viewModel.screenState.value
        assertTrue(state is PokedexBaseState.Content)
        assertEquals(expected, (state as PokedexBaseState.Content).data)
    }
}

class MainDispatcherRule(
    val dispatcher: TestDispatcher = StandardTestDispatcher()
) : TestWatcher() {

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun starting(description: Description) {
        Dispatchers.setMain(dispatcher)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun finished(description: Description) {
        Dispatchers.resetMain()
    }
}