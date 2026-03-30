package com.luvsoft.rooom.network.api

import com.luvsoft.rooom.network.entities.CountryFavoriteEntity

interface CountryApi {

    suspend fun getAllFavorites(callback: (List<CountryFavoriteEntity>) -> Unit )

    suspend fun addFavorite(foodEntity: CountryFavoriteEntity, callback: (Long) -> Unit)

    suspend fun deleteFavorite(foodEntity: CountryFavoriteEntity): Int
}