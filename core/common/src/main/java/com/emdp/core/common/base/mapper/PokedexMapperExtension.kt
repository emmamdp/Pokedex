package com.emdp.core.common.base.mapper

fun <I, O> I.mapWith(mapper: PokedexMapper<I, O>): O =
    mapper.map(this)

fun <I, O> Iterable<I>.mapWith(mapper: PokedexMapper<I, O>): List<O> =
    this.map { mapper.map(it) }