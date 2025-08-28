package com.emdp.domain.usecase.pokemonlist

import androidx.paging.PagingData
import com.emdp.domain.model.PokemonListModel
import com.emdp.domain.repository.PokedexRepository
import kotlinx.coroutines.flow.Flow

class GetPokemonListUseCaseImpl(
    private val repository: PokedexRepository
) : GetPokemonListUseCase {

    override fun invoke(): Flow<PagingData<PokemonListModel>> = repository.getPagedPokemonList()
}