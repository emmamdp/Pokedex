package com.emdp.features.splash.presentation

import com.emdp.core.common.base.presentation.PokedexBaseState.NavigateToNextView
import com.emdp.core.common.base.presentation.PokedexBaseState.ShowScreenError
import com.emdp.core.navigation.PokedexDestination.OpenPokemonList
import com.emdp.domain.common.base.result.PokedexResult.PkError
import com.emdp.domain.common.base.result.PokedexResult.PkSuccess
import com.emdp.domain.model.base.NoParams
import com.emdp.domain.model.error.PokedexGenericError.NoConnection
import com.emdp.domain.usecase.datastore.ShouldSyncPokemonTodayUseCase
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertTrue
import org.junit.Test
import kotlin.test.assertIs

internal class PokedexSplashViewModelTest {

    private val syncUseCase: ShouldSyncPokemonTodayUseCase = mockk()
    private val viewModel = PokedexSplashViewModel(syncUseCase)

    @Test
    fun `start navigates to PokemonList on success`() = runTest {
        coEvery { syncUseCase(NoParams) } returns PkSuccess(true)

        viewModel.initialize()

        val state = viewModel.screenState.value
        val navigate = assertIs<NavigateToNextView>(state)
        assertTrue(navigate.destination is OpenPokemonList)
    }

    @Test
    fun `start sets Error state when use case fails`() = runTest {
        coEvery { syncUseCase.invoke(NoParams) } returns PkError(NoConnection)

        viewModel.initialize()

        assertIs<ShowScreenError>(viewModel.screenState.value)
    }
}