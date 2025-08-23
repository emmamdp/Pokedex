package com.emdp.data.common.base

import com.emdp.domain.common.base.result.PokedexResult

suspend inline fun <T> safeCall(crossinline block: suspend () -> T): PokedexResult<T> =
    try {
        PokedexResult.Success(block())
    } catch (t: Throwable) {
        PokedexResult.Error(PokedexExceptionMapper.toPokedexGenericError(t))
    }