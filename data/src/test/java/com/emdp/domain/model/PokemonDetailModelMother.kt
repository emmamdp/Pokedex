package com.emdp.domain.model

import com.emdp.domain.model.types.PokemonType

internal object PokemonDetailModelMother {

    private val base = PokemonDetailModel(
        id = 1,
        name = "Bulbasaur",
        imageUrl = "https://img",
        types = listOf(PokemonType.GRASS, PokemonType.POISON),
        height = 7,
        weight = 69,
        stats = StatModelMother.mock()
    )

    fun mock() = base
}