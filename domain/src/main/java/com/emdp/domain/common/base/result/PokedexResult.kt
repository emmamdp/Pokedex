package com.emdp.domain.common.base.result

import com.emdp.domain.model.error.PokedexGenericError

sealed interface PokedexResult<out T> {
    data class PkSuccess<T>(val pkData: T) : PokedexResult<T>
    data class PkError(
        val pkError: PokedexGenericError? = null
    ) : PokedexResult<Nothing>
}