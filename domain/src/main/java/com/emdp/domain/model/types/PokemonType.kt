package com.emdp.domain.model.types

enum class PokemonType(val pokemonType: String) {
    NORMAL("normal"),
    FIGHTING("fighting"),
    FLYING("flying"),
    POISON("poison"),
    GROUND("ground"),
    ROCK("rock"),
    BUG("bug"),
    GHOST("ghost"),
    STEEL("steel"),
    FIRE("fire"),
    WATER("water"),
    GRASS("grass"),
    ELECTRIC("electric"),
    PSYCHIC("psychic"),
    ICE("ice"),
    DRAGON("dragon"),
    DARK("dark"),
    FAIRY("fairy"),
    SHADOW("shadow"),
    UNKNOWN("unknown");

    companion object {
        fun fromPokemonType(type: String?): PokemonType {
            val key = type?.trim()?.lowercase() ?: return UNKNOWN
            return entries.firstOrNull { it.pokemonType == key } ?: UNKNOWN
        }
    }

    fun displayName(): String =
        name.lowercase()
            .replaceFirstChar { firstChar ->
                if (firstChar.isLowerCase())
                    firstChar.titlecase()
                else
                    firstChar.toString()
            }
}