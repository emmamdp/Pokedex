package com.emdp.domain.common.base.result

import com.emdp.domain.model.error.PokedexGenericError

fun <T> PokedexResult<T>.getOrNull(): T? =
    (this as? PokedexResult.PkSuccess)?.pkData

inline fun <T> PokedexResult<T>.onSuccess(block: (T) -> Unit): PokedexResult<T> {
    if (this is PokedexResult.PkSuccess) block(pkData)
    return this
}

inline fun <T> PokedexResult<T>.onError(block: (error: PokedexGenericError?) -> Unit): PokedexResult<T> {
    if (this is PokedexResult.PkError) block(pkError)
    return this
}

inline fun <T, R> PokedexResult<T>.fold(
    onSuccess: (T) -> R,
    onError: (error: PokedexGenericError?) -> R
): R = when (this) {
    is PokedexResult.PkSuccess -> onSuccess(pkData)
    is PokedexResult.PkError -> onError(pkError)
}