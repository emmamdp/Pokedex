package com.emdp.domain.model

import com.emdp.domain.model.types.PokemonType

internal object PokemonDetailModelMother {

    private val base = PokemonDetailModel(
        id = 25,
        name = "Pikachu",
        imageUrl = "https://example.test/25.png",
        types = listOf(PokemonType.ELECTRIC),
        height = 4,
        weight = 60,
        stats = StatModelMother.mock()
    )

    fun mock() = base

    fun mockOtherPokemon() = base.copy(
        id = 1,
        name = "Bulbasaur"
    )
}