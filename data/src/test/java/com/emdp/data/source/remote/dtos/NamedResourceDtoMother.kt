package com.emdp.data.source.remote.dtos

internal object NamedResourceDtoMother {

    private val base = NamedResourceDto(name = "hp", url = "")

    fun mock1() = base

    fun mock2() = base.copy(name = "special-defense", url = "")

    fun mock3() = base.copy(name = "attack", url = "")

    fun mock4() = base.copy(name = "poison", url = "")

    fun mock5() = base.copy(name = "grass", url = "")
}