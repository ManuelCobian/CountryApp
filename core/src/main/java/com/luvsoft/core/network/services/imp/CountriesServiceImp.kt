package com.luvsoft.core.network.services.imp

import com.luvsoft.core.network.models.CountryResponse
import com.luvsoft.core.network.services.CountriesService
import retrofit2.Retrofit
import javax.inject.Inject

class CountriesServiceImp @Inject constructor(private val retrofit: Retrofit) : CountriesService  {
    override suspend fun getAllCountries(fields: String): List<CountryResponse> {
        val service = retrofit.create(CountriesService::class.java)
        return service.getAllCountries(fields)
    }

    override suspend fun getCountryByName(
        name: String,
        fullText: Boolean
    ): List<CountryResponse> {
        val service = retrofit.create(CountriesService::class.java)
        return service.getCountryByName(name = name, fullText = fullText)
    }
}
