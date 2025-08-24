package com.emdp.features.splash.presentation

import com.emdp.core.common.base.presentation.PokedexBaseState
import com.emdp.core.common.base.presentation.PokedexBaseViewModel
import com.emdp.core.navigation.PokedexDestination
import com.emdp.domain.common.base.result.PokedexResult.PkError
import com.emdp.domain.common.base.result.PokedexResult.PkSuccess
import com.emdp.domain.model.base.NoParams
import com.emdp.domain.usecase.datastore.ShouldSyncPokemonListUseCase
import com.emdp.domain.usecase.pokemonlist.SyncPokemonListUseCase

class PokedexSplashViewModel(
    private val syncUseCase: ShouldSyncPokemonListUseCase,
    private val syncPokemonListUseCase: SyncPokemonListUseCase
) : PokedexBaseViewModel<Unit>() {

    override fun initialScreenState(): PokedexBaseState<Unit> = PokedexBaseState.Loading

    suspend fun initialize() {
        when (val result = syncUseCase.invoke(NoParams)) {
            is PkSuccess -> if (result.pkData) {
                getAllPokemons()
            } else {
                navigateToPokemonListScreen()
            }

            is PkError -> setError(message = result.pkError.toString())
        }
    }

    private suspend fun getAllPokemons() {
        when (val result = syncPokemonListUseCase(NoParams)) {
            is PkSuccess -> navigateToPokemonListScreen()
            is PkError -> setError(message = result.pkError.toString())
        }
    }

    private fun navigateToPokemonListScreen() =
        navigateTo(PokedexDestination.OpenPokemonList)
}