package com.emdp.data.source.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.emdp.data.source.local.database.dao.PokemonListDao
import com.emdp.data.source.local.database.entities.PokemonListEntity

@Database(
    entities = [PokemonListEntity::class],
    version = 1,
    exportSchema = false
)
abstract class PokedexDatabase : RoomDatabase() {
    abstract fun pokemonListDao(): PokemonListDao
}