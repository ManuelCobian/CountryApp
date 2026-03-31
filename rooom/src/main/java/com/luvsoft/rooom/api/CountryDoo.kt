package com.luvsoft.rooom.api

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.luvsoft.rooom.network.entities.CountryFavoriteEntity

@Dao
interface CountryDoo {

    @Query("SELECT * FROM country_favorites ")
    suspend fun getAllFavorites(): List<CountryFavoriteEntity>

    @Insert
    suspend fun insertFavorite(country: CountryFavoriteEntity): Long

    @Delete
    suspend fun deleteFavorite(country: CountryFavoriteEntity): Int
}