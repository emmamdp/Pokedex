package com.emdp.domain.common.base

import com.emdp.domain.model.error.PokedexGenericError

sealed interface PokedexResult<out T> {
    data class Success<T>(val data: T) : PokedexResult<T>
    data class Error(
        val error: PokedexGenericError? = null
    ) : PokedexResult<Nothing>
}