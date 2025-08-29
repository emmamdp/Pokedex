package com.emdp.data.source.remote.dtos

internal object StatDtoMother {

    private val base = StatDto(baseStat = 45, stat = NamedResourceDtoMother.mock1())

    fun mock() = listOf(
        base,
        base.copy(
            baseStat = 65,
            stat = NamedResourceDtoMother.mock2()
        ),
        base.copy(
            baseStat = 49,
            stat = NamedResourceDtoMother.mock3()
        )
    )
}