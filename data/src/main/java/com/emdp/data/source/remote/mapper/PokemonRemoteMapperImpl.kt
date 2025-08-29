package com.emdp.data.source.remote.mapper

import com.emdp.data.source.remote.dtos.PokemonDetailResponseDto
import com.emdp.data.source.remote.dtos.PokemonListResponseDto
import com.emdp.domain.model.PokemonDetailModel
import com.emdp.domain.model.PokemonListModel
import com.emdp.domain.model.StatModel
import com.emdp.domain.model.types.PokemonType


class PokemonRemoteMapperImpl : PokemonRemoteMapper {

    override fun toModel(responseDto: PokemonListResponseDto): List<PokemonListModel> =
        responseDto.results?.map { pokemon ->
            val id = extractIdFromUrl(pokemon.url)
            PokemonListModel(
                id = id,
                name = pokemon.name
            )
        } ?: emptyList()

    override fun toModel(responseDto: PokemonDetailResponseDto): PokemonDetailModel {
        val types = responseDto.types
            ?.sortedBy { it.slot }
            ?.map { slot -> PokemonType.fromPokemonType(slot.type.name) }
            .orEmpty()

        val baseAttributes = responseDto.stats
            ?.map { StatModel(name = it.stat.name, base = it.baseStat) }
            .orEmpty()

        return PokemonDetailModel(
            id = responseDto.id,
            name = responseDto.name.capitalizedName(),
            imageUrl = responseDto.sprites?.frontDefault,
            types = types,
            height = responseDto.height,
            weight = responseDto.weight,
            stats = baseAttributes
        )
    }

    private fun extractIdFromUrl(url: String): Int {
        val match = PK_URL_REGEX.find(url) ?: error(ERROR_INVALID_URL + url)
        return match.groupValues[1].toInt()
    }

    private fun String.capitalizedName(): String =
        this.replaceFirstChar { firstChar ->
            if (firstChar.isLowerCase())
                firstChar.titlecase()
            else
                firstChar.toString()
        }

    private companion object {
        val PK_URL_REGEX = Regex(".*/pokemon/(\\d+)/?$")
        const val ERROR_INVALID_URL = "URL de Pokémon no válida: "
    }
}