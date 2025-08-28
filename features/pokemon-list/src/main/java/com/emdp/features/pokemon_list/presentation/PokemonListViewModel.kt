package com.emdp.features.pokemon_list.presentation

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.emdp.core.common.base.presentation.PokedexBaseState
import com.emdp.core.common.base.presentation.PokedexBaseViewModel
import com.emdp.core.navigation.PokedexDestination
import com.emdp.domain.model.PokemonListModel
import com.emdp.domain.usecase.pokemonlist.GetPokemonListUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class PokemonListViewModel(
    getPokemonListUseCase: GetPokemonListUseCase
) : PokedexBaseViewModel<Unit>() {

    private val _favorites = MutableStateFlow<Set<Int>>(emptySet())
    val favorites = _favorites.asStateFlow()

    val pokemonList: Flow<PagingData<PokemonListModel>> =
        getPokemonListUseCase().cachedIn(viewModelScope)

    override fun initialScreenState(): PokedexBaseState<Unit> =
        PokedexBaseState.Content(Unit)

    fun onPokemonClick(id: Int) {
        navigateTo(PokedexDestination.OpenPokemonDetail(id))
    }

    fun onToggleFavorite(id: Int) {
        _favorites.value = _favorites.value.let { set ->
            if (set.contains(id)) set - id else set + id
        }
    }
}