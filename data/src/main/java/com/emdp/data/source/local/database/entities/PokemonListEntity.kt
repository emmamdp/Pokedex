package com.emdp.data.source.local.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pokemon_list")
data class PokemonListEntity(
    @PrimaryKey val id: Int,
    val name: String,
)
