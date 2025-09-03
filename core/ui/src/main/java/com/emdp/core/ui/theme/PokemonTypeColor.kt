package com.emdp.core.ui.theme

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance
import com.emdp.domain.model.types.PokemonType

object PokemonTypeColor {

    fun backgroundFor(type: PokemonType): Color = when (type) {
        PokemonType.FIRE -> Color(0xFFEB8A6E)
        PokemonType.WATER -> Color(0xFF7AA9E6)
        PokemonType.GRASS -> Color(0xFF8CCB87)
        PokemonType.ELECTRIC -> Color(0xFFF2D66B)
        PokemonType.ICE -> Color(0xFFBDE4E0)
        PokemonType.FIGHTING -> Color(0xFFD16E6E)
        PokemonType.POISON -> Color(0xFFBC86C7)
        PokemonType.GROUND -> Color(0xFFE7D29B)
        PokemonType.FLYING -> Color(0xFFB3A7E9)
        PokemonType.PSYCHIC -> Color(0xFFF29AB3)
        PokemonType.BUG -> Color(0xFFB7C768)
        PokemonType.ROCK -> Color(0xFFC9BA84)
        PokemonType.GHOST -> Color(0xFFA59AC8)
        PokemonType.DRAGON -> Color(0xFFA393F3)
        PokemonType.DARK -> Color(0xFFA28E7E)
        PokemonType.STEEL -> Color(0xFFC9CBD7)
        PokemonType.FAIRY -> Color(0xFFF0B9CF)
        PokemonType.NORMAL -> Color(0xFFC8C4A7)
        PokemonType.SHADOW -> Color(0xFFA0A0A0)
        PokemonType.UNKNOWN -> Color(0xFFB0B0B0)
    }

    fun onTextColorFor(background: Color): Color =
        if (background.luminance() >= 0.6f) Color.Black else PkOnPrimaryWhite

    fun dominantType(types: List<PokemonType>): PokemonType =
        types.firstOrNull() ?: PokemonType.UNKNOWN
}

val ProgressBarColor = PkOnPrimaryWhite