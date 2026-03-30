package com.luvsoft.rooom.network.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "country_favorites")
data class CountryFavoriteEntity(
    @PrimaryKey
    val cca3: String,
    val commonName: String,
    val officialName: String,
    val capital: String,
    val flagUrl: String?,
    val coatOfArmsUrl: String?,
    val region: String?,
    val subregion: String?,
    val population: Long?,
    val area: Double?
)