package com.emdp.features.splash.presentation

import androidx.lifecycle.viewModelScope
import com.emdp.core.common.base.presentation.PokedexBaseState
import com.emdp.core.common.base.presentation.PokedexBaseViewModel
import com.emdp.core.navigation.PokedexDestination
import com.emdp.domain.common.base.result.PokedexResult
import com.emdp.domain.model.base.NoParams
import com.emdp.domain.usecase.datastore.ShouldSyncPokemonTodayUseCase
import kotlinx.coroutines.launch

class PokedexSplashViewModel(
    private val syncUseCase: ShouldSyncPokemonTodayUseCase
) : PokedexBaseViewModel<Unit>() {

    override fun initialScreenState(): PokedexBaseState<Unit> = PokedexBaseState.Loading

    fun initialize() {
        viewModelScope.launch {
            when (val result = syncUseCase.invoke(NoParams)) {
                is PokedexResult.Success -> {
                    navigateTo(PokedexDestination.OpenPokemonList)
                }
                is PokedexResult.Error -> {
                    setError(message = result.pkError.toString())
                }
            }
        }
    }
}