package com.emdp.domain.common.base

import com.emdp.domain.model.error.PokedexGenericError

fun <T> PokedexResult<T>.getOrNull(): T? =
    (this as? PokedexResult.Success)?.data

inline fun <T> PokedexResult<T>.onSuccess(block: (T) -> Unit): PokedexResult<T> {
    if (this is PokedexResult.Success) block(data)
    return this
}

inline fun <T> PokedexResult<T>.onError(block: (error: PokedexGenericError?) -> Unit): PokedexResult<T> {
    if (this is PokedexResult.Error) block(error)
    return this
}

inline fun <T, R> PokedexResult<T>.fold(
    onSuccess: (T) -> R,
    onError: (error: PokedexGenericError?) -> R
): R = when (this) {
    is PokedexResult.Success -> onSuccess(data)
    is PokedexResult.Error -> onError(error)
}