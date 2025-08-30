package com.emdp.features.pokemon_detail.presentation

import androidx.lifecycle.viewModelScope
import com.emdp.core.common.base.presentation.PokedexBaseViewModel
import com.emdp.domain.common.base.result.PokedexResult.PkError
import com.emdp.domain.common.base.result.PokedexResult.PkSuccess
import com.emdp.domain.model.PokemonDetailModel
import com.emdp.domain.usecase.pokemondetail.GetPokemonDetailUseCase
import kotlinx.coroutines.launch

class PokemonDetailViewModel(
    private val getPokemonDetail: GetPokemonDetailUseCase
) : PokedexBaseViewModel<PokemonDetailModel>() {

    private var lastPokemonId: Int? = null

    suspend fun load(pokemonId: Int) {
        lastPokemonId = pokemonId
        when (val result = getPokemonDetail(pokemonId)) {
            is PkSuccess -> setContent(result.pkData)
            is PkError -> setError(result.pkError.toString())
        }
    }

    fun retry() {
        viewModelScope.launch {
            lastPokemonId?.let { load(it) }
        }
    }
}