package com.emdp.features.splash.presentation

import com.emdp.core.common.base.presentation.PokedexBaseState
import com.emdp.core.common.base.presentation.PokedexBaseViewModel
import com.emdp.core.navigation.PokedexDestination

class PokedexSplashViewModel : PokedexBaseViewModel<Unit>() {

    override fun initialScreenState(): PokedexBaseState<Unit> = PokedexBaseState.Loading

    fun initialize() {
        navigateTo(PokedexDestination.OpenPokemonList)
    }
}