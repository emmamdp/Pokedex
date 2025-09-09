package com.emdp.features.pokemon_list.presentation

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.filter
import com.emdp.core.common.base.presentation.PokedexBaseState
import com.emdp.core.common.base.presentation.PokedexBaseViewModel
import com.emdp.core.navigation.PokedexDestination
import com.emdp.domain.model.PokemonListModel
import com.emdp.domain.usecase.pokemonlist.GetPokemonListUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map

class PokemonListViewModel(
    getPokemonListUseCase: GetPokemonListUseCase
) : PokedexBaseViewModel<Unit>() {

    private val _favorites = MutableStateFlow<Set<Int>>(emptySet())
    val favorites = _favorites.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery = _searchQuery.asStateFlow()

    private val baseList: Flow<PagingData<PokemonListModel>> =
        getPokemonListUseCase().cachedIn(viewModelScope)

    @OptIn(ExperimentalCoroutinesApi::class)
    val pokemonList: Flow<PagingData<PokemonListModel>> =
        _searchQuery
            .map { it.trim() }
            .flatMapLatest { q ->
                if (q.isBlank()) {
                    baseList
                } else {
                    baseList.map { paging ->
                        paging.filter { model ->
                            model.name.contains(q, ignoreCase = true) ||
                                    model.id.toString() == q.removePrefix("#")
                        }
                    }
                }
            }

    override fun initialScreenState(): PokedexBaseState<Unit> = PokedexBaseState.Loading

    fun onPokemonClick(id: Int) {
        navigateTo(destination = PokedexDestination.OpenPokemonDetail(id))
    }

    fun onToggleFavorite(id: Int) {
        _favorites.value = _favorites.value.let { set ->
            if (set.contains(id)) set - id else set + id
        }
    }

    fun onSearchQueryChange(query: String) {
        _searchQuery.value = query
    }

    fun clearSearch() {
        _searchQuery.value = ""
    }
}