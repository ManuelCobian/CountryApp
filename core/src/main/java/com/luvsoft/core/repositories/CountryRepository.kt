package com.luvsoft.core.repositories

import com.luvsoft.core.ApiResponseStatus
import com.luvsoft.core.makeNetworkCall
import com.luvsoft.core.repositories.mappers.toDomain
import com.luvsoft.core.network.services.CountriesService
import com.luvsoft.core.ui.Country
import javax.inject.Inject

class CountryRepository @Inject constructor(private val service: CountriesService)  {

    suspend fun getAllCountries(): ApiResponseStatus<List<Country>> = makeNetworkCall {
        val fieldsSelected = listOf("name", "capital", "flags")
            .joinToString(",")
        service.getAllCountries(fieldsSelected).map { it.toDomain() }
    }

    suspend fun getCountryByName(name: String): ApiResponseStatus<List<Country>> = makeNetworkCall {
        service.getCountryByName(name).map { it.toDomain() }
    }
}