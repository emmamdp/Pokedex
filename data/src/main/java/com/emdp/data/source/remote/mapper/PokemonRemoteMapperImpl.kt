package com.emdp.data.source.remote.mapper

import com.emdp.data.source.remote.dtos.PokemonListResponseDto
import com.emdp.domain.model.PokemonListModel


class PokemonRemoteMapperImpl: PokemonRemoteMapper {

    override fun toModel(responseDto: PokemonListResponseDto): List<PokemonListModel> =
        responseDto.results?.map { pokemon ->
            val id = extractIdFromUrl(pokemon.url)
            PokemonListModel(
                id = id,
                name = pokemon.name
            )
        } ?: emptyList()

    private fun extractIdFromUrl(url: String): Int {
        val match = PK_URL_REGEX.find(url) ?: error(ERROR_INVALID_URL + url)
        return match.groupValues[1].toInt()
    }

    private companion object {
        val PK_URL_REGEX = Regex(".*/pokemon/(\\d+)/?$")
        const val ERROR_INVALID_URL = "URL de Pokémon no válida: "
    }
}