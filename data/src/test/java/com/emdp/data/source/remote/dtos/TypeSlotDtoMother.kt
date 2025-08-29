package com.emdp.data.source.remote.dtos

internal object TypeSlotDtoMother {

    private val base = TypeSlotDto(
        slot = 2,
        type = NamedResourceDtoMother.mock4()
    )

    fun mock() = listOf(
        base,
        base.copy(
            slot = 1,
            type = NamedResourceDtoMother.mock5()
        )
    )
}