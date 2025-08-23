package com.emdp.data.common.base

import android.database.sqlite.SQLiteException
import com.emdp.domain.model.error.PokedexGenericError
import com.squareup.moshi.JsonDataException
import com.squareup.moshi.JsonEncodingException
import retrofit2.HttpException
import java.io.IOException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import kotlin.coroutines.cancellation.CancellationException

object PokedexExceptionMapper {

    fun toPokedexGenericError(t: Throwable): PokedexGenericError = when (t) {
        is CancellationException -> throw t
        is UnknownHostException, is ConnectException -> PokedexGenericError.NoConnection
        is SocketTimeoutException -> PokedexGenericError.Timeout
        is IOException -> PokedexGenericError.NoConnection
        is HttpException -> when (t.code()) {
            401 -> PokedexGenericError.Unauthorized
            404 -> PokedexGenericError.NotFound
            in 500..599 -> PokedexGenericError.ServerError(code = t.code())
            else -> PokedexGenericError.UnknownError(t)
        }

        is JsonDataException, is JsonEncodingException -> PokedexGenericError.SerializationError
        is SQLiteException -> PokedexGenericError.DatabaseError
        else -> PokedexGenericError.UnknownError(t)
    }
}