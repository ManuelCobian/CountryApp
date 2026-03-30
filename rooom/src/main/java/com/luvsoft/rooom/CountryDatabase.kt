package com.luvsoft.rooom

import androidx.room.Database
import androidx.room.RoomDatabase
import com.luvsoft.rooom.network.entities.CountryFavoriteEntity
import com.luvsoft.rooom.services.CountryDoo

@Database(
    entities = [
        CountryFavoriteEntity::class
    ], version = 1
)
abstract class CountryDatabase : RoomDatabase() {
    abstract fun countryDao(): CountryDoo
}