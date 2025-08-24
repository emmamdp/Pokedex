package com.emdp.data.source.local.database.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.emdp.data.source.local.database.entities.PokemonListEntity

@Dao
interface PokemonListDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(items: List<PokemonListEntity>)

    @Query("SELECT id, name FROM pokemon_list ORDER BY id ASC")
    fun pagingSource(): PagingSource<Int, PokemonListEntity>
}