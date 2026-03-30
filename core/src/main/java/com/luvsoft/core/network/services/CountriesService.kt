package com.luvsoft.core.network.services

import com.luvsoft.core.Constants.PATH_COUNTRIES_ALL
import com.luvsoft.core.Constants.PATH_COUNTRY
import com.luvsoft.core.network.models.CountryResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CountriesService {

    @GET(PATH_COUNTRIES_ALL)
    suspend fun getAllCountries(
        @Query("fields") fields: String
    ): List<CountryResponse>


    @GET(PATH_COUNTRY)
    suspend fun getCountryByName(
        @Path("name") name: String,
        @Query("fullText") fullText: Boolean = false
    ): List<CountryResponse>
}
