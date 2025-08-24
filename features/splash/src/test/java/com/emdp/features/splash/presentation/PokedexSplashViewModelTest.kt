package com.emdp.features.splash.presentation

import com.emdp.core.common.base.presentation.PokedexBaseState
import com.emdp.core.navigation.PokedexDestination
import com.emdp.domain.common.base.result.PokedexResult.PkError
import com.emdp.domain.common.base.result.PokedexResult.PkSuccess
import com.emdp.domain.model.base.NoParams
import com.emdp.domain.usecase.datastore.ShouldSyncPokemonListUseCase
import com.emdp.domain.usecase.pokemonlist.SyncPokemonListUseCase
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

internal class PokedexSplashViewModelTest {

    private val shouldSyncUseCase: ShouldSyncPokemonListUseCase = mockk()
    private val syncPokemonListUseCase: SyncPokemonListUseCase = mockk()

    private lateinit var viewModel: PokedexSplashViewModel

    @Before
    fun setUp() {
        viewModel = PokedexSplashViewModel(
            syncUseCase = shouldSyncUseCase,
            syncPokemonListUseCase = syncPokemonListUseCase
        )
    }

    @Test
    fun `initial state is Loading`() {
        val state = viewModel.screenState.value

        assertTrue(state is PokedexBaseState.Loading)
    }

    @Test
    fun `initialize navigates when no sync is needed`() = runTest {
        coEvery { shouldSyncUseCase.invoke(NoParams) } returns PkSuccess(false)

        viewModel.initialize()

        val state = viewModel.screenState.value
        assertTrue(state is PokedexBaseState.NavigateToNextView)
        assertEquals(
            PokedexDestination.OpenPokemonList,
            (state as PokedexBaseState.NavigateToNextView).destination
        )
        coVerify(exactly = 1) { shouldSyncUseCase.invoke(NoParams) }
        coVerify(exactly = 0) { syncPokemonListUseCase.invoke(NoParams) }
        confirmVerified(shouldSyncUseCase, syncPokemonListUseCase)
    }

    @Test
    fun `initialize syncs and navigates when sync is needed`() = runTest {
        coEvery { shouldSyncUseCase.invoke(NoParams) } returns PkSuccess(true)
        coEvery { syncPokemonListUseCase.invoke(NoParams) } returns PkSuccess(Unit)

        viewModel.initialize()

        val state = viewModel.screenState.value
        assertTrue(state is PokedexBaseState.NavigateToNextView)
        assertEquals(
            PokedexDestination.OpenPokemonList,
            (state as PokedexBaseState.NavigateToNextView).destination
        )
        coVerify(exactly = 1) { shouldSyncUseCase.invoke(NoParams) }
        coVerify(exactly = 1) { syncPokemonListUseCase.invoke(NoParams) }
        confirmVerified(shouldSyncUseCase, syncPokemonListUseCase)
    }

    @Test
    fun `initialize sets error when shouldSync fails`() = runTest {
        coEvery { shouldSyncUseCase.invoke(NoParams) } returns PkError(pkError = mockk(relaxed = true))

        viewModel.initialize()

        val state = viewModel.screenState.value
        assertTrue(state is PokedexBaseState.ShowScreenError)
        coVerify(exactly = 1) { shouldSyncUseCase.invoke(NoParams) }
        coVerify(exactly = 0) { syncPokemonListUseCase.invoke(NoParams) }
        confirmVerified(shouldSyncUseCase, syncPokemonListUseCase)
    }

    @Test
    fun `initialize sets error when syncPokemonList fails`() = runTest {
        coEvery { shouldSyncUseCase.invoke(NoParams) } returns PkSuccess(true)
        coEvery { syncPokemonListUseCase.invoke(NoParams) } returns PkError(pkError = mockk(relaxed = true))

        viewModel.initialize()

        val state = viewModel.screenState.value
        assertTrue(state is PokedexBaseState.ShowScreenError)
        coVerify(exactly = 1) { shouldSyncUseCase.invoke(NoParams) }
        coVerify(exactly = 1) { syncPokemonListUseCase.invoke(NoParams) }
        confirmVerified(shouldSyncUseCase, syncPokemonListUseCase)
    }
}