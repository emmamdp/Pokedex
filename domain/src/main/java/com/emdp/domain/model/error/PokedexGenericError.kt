package com.emdp.domain.model.error

sealed interface PokedexGenericError {
    data object NoConnection : PokedexGenericError
    data object Timeout : PokedexGenericError
    data object Unauthorized : PokedexGenericError
    data object NotFound : PokedexGenericError
    data object SerializationError : PokedexGenericError
    data object DatabaseError : PokedexGenericError
    data class ServerError(val code: Int? = null) : PokedexGenericError
    data class UnknownError(val cause: Throwable? = null) : PokedexGenericError
}