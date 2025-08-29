package com.emdp.domain.usecase.pokemondetail

import com.emdp.domain.common.base.result.PokedexResult
import com.emdp.domain.model.PokemonDetailModel
import com.emdp.domain.repository.PokedexRepository

class GetPokemonDetailUseCaseImpl(
    private val repository: PokedexRepository
) : GetPokemonDetailUseCase {

    override suspend fun invoke(params: Int): PokedexResult<PokemonDetailModel> =
        repository.getPokemonDetail(params)
}