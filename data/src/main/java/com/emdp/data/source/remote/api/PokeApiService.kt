package com.emdp.data.source.remote.api

import com.emdp.data.source.remote.dtos.PokemonListResponseDto
import retrofit2.http.GET
import retrofit2.http.Query

interface PokeApiService {

    @GET("pokemon")
    suspend fun getPokemonList(
        @Query("offset") offset: Int,
        @Query("limit") limit: Int
    ): PokemonListResponseDto
}