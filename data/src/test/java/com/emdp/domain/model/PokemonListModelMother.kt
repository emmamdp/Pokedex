package com.emdp.domain.model

internal object PokemonListModelMother {

    private const val NAME_1 = "bulbasaur"
    private const val NAME_2 = "ivysaur"

    private const val ID_1 = 1
    private const val ID_2 = 2

    private val base = PokemonListModel(
        id = ID_1,
        name = NAME_1
    )

    fun mock() = listOf(
        base,
        base.copy(
            id = ID_2,
            name = NAME_2
        )
    )
}