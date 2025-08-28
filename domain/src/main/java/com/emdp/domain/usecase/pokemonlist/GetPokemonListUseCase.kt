package com.emdp.domain.usecase.pokemonlist

import androidx.paging.PagingData
import com.emdp.domain.model.PokemonListModel
import kotlinx.coroutines.flow.Flow

interface GetPokemonListUseCase {
    operator fun invoke(): Flow<PagingData<PokemonListModel>>
}