package com.emdp.data.source.local.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.emdp.data.source.local.database.entities.PokemonListEntity

@Dao
interface PokemonListDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(items: List<PokemonListEntity>)
}