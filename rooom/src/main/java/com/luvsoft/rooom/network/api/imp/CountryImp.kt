package com.luvsoft.rooom.network.api.imp

import com.luvsoft.rooom.network.api.CountryApi
import com.luvsoft.rooom.network.entities.CountryFavoriteEntity
import com.luvsoft.rooom.api.CountryDoo
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CountryImp @Inject constructor(private val countryDoo: CountryDoo): CountryApi {
    override suspend fun getAllFavorites(callback: (List<CountryFavoriteEntity>) -> Unit) {
        callback(countryDoo.getAllFavorites())
    }

    override suspend fun addFavorite(
        favorite: CountryFavoriteEntity,
        callback: (Long) -> Unit
    ) {
        val result = countryDoo.insertFavorite(favorite)
        callback(result)
    }

    override suspend fun deleteFavorite(foodEntity: CountryFavoriteEntity): Int {
        return countryDoo.deleteFavorite(foodEntity)
    }
}