package com.emdp.domain.model

internal object StatModelMother {

    private val base = StatModel(
        name = "hp",
        base = 35
    )

    fun mock() = listOf(
        base,
        base.copy(name = "attack", base = 55),
        base.copy(name = "defense", base = 40),
        base.copy(name = "speed", base = 90)
    )
}