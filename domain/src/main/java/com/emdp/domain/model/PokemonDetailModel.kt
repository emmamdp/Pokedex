package com.emdp.domain.model

import com.emdp.domain.model.types.PokemonType

data class PokemonDetailModel(
    val id: Int,
    val name: String,
    val imageUrl: String?,
    val types: List<PokemonType>,
    val height: Int?,
    val weight: Int?,
    val stats: List<StatModel>
)

data class StatModel(
    val name: String,
    val base: Int
)