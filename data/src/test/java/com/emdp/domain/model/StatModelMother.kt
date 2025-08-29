package com.emdp.domain.model

internal object StatModelMother {

    private val base = StatModel(name = "hp", base = 45)

    fun mock() = listOf(
        base,
        base.copy(name = "special-defense", base = 65),
        base.copy(name = "attack", base = 49)
    )
}