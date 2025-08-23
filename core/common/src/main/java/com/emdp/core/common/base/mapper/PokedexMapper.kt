package com.emdp.core.common.base.mapper

interface PokedexMapper<I, O> {
    fun map(input: I): O
}