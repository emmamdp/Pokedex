package com.emdp.features.splash.presentation

import com.emdp.core.common.base.presentation.PokedexBaseState
import com.emdp.core.common.base.presentation.PokedexBaseViewModel
import com.emdp.core.navigation.PokedexDestination
import com.emdp.domain.common.base.result.PokedexResult
import com.emdp.domain.model.base.NoParams
import com.emdp.domain.usecase.datastore.ShouldSyncPokemonTodayUseCase

class PokedexSplashViewModel(
    private val syncUseCase: ShouldSyncPokemonTodayUseCase
) : PokedexBaseViewModel<Unit>() {

    override fun initialScreenState(): PokedexBaseState<Unit> = PokedexBaseState.Loading

    suspend fun initialize() {
        when (val result = syncUseCase.invoke(NoParams)) {
            is PokedexResult.PkSuccess -> {
                navigateTo(PokedexDestination.OpenPokemonList)
            }

            is PokedexResult.PkError -> {
                setError(message = result.pkError.toString())
            }
        }
    }
}