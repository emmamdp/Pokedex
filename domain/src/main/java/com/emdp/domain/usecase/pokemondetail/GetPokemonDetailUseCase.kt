package com.emdp.domain.usecase.pokemondetail

import com.emdp.domain.common.base.usecase.PokedexBaseUseCase
import com.emdp.domain.model.PokemonDetailModel

interface GetPokemonDetailUseCase : PokedexBaseUseCase<Int, PokemonDetailModel>