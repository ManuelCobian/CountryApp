package com.luvsoft.rooom.di

import android.content.Context
import androidx.room.Room
import com.luvsoft.rooom.CountryDatabase
import com.luvsoft.rooom.network.api.CountryApi
import com.luvsoft.rooom.network.api.imp.CountryImp
import com.luvsoft.rooom.services.CountryDoo
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
abstract class DataCountrySourceModule {
    @Singleton
    @Binds
    abstract fun bindDataSource(imp: CountryImp): CountryApi
}

@InstallIn(SingletonComponent::class)
@Module
object RoomModule {
    @Provides
    fun provideDao(database: CountryDatabase): CountryDoo = database.countryDao()

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext app: Context): CountryDatabase = Room.databaseBuilder(
        context = app,
        CountryDatabase::class.java,
        "CountryDB"
    ).build()
}