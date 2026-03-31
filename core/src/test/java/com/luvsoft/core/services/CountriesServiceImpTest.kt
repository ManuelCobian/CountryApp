package com.luvsoft.core.services

import com.luvsoft.core.network.models.CountryNameResponse
import com.luvsoft.core.network.models.CountryResponse
import com.luvsoft.core.network.models.FlagsResponse
import com.luvsoft.core.network.services.CountriesService
import com.luvsoft.core.network.services.imp.CountriesServiceImp
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import retrofit2.Retrofit
import kotlin.test.assertEquals

class CountriesServiceImpTest {

    private lateinit var retrofit: Retrofit
    private lateinit var apiService: CountriesService
    private lateinit var countriesServiceImp: CountriesServiceImp

    @Before
    fun setUp() {
        retrofit = mock()
        apiService = mock()
        countriesServiceImp = CountriesServiceImp(retrofit)
    }

    @Test
    fun `getAllCountries should return list of countries`() = runTest {
        // Arrange
        val fields = "name,capital,flags"
        val expectedResponse = listOf(
            CountryResponse(
                name = CountryNameResponse(common = "Mexico", official = "United Mexican States"),
                capital = listOf("Mexico City"),
                flags = FlagsResponse(
                    png = "https://flagcdn.com/w320/mx.png",
                    svg = "https://flagcdn.com/mx.svg"
                )
            )
        )

        whenever(retrofit.create(CountriesService::class.java)).thenReturn(apiService)
        whenever(apiService.getAllCountries(fields)).thenReturn(expectedResponse)

        // Act
        val result = countriesServiceImp.getAllCountries(fields)

        // Assert
        assertEquals(expectedResponse, result)
        verify(retrofit).create(CountriesService::class.java)
        verify(apiService).getAllCountries(fields)
    }

    @Test
    fun `getCountryByName should return country list`() = runTest {
        // Arrange
        val countryName = "Mexico"
        val fullText = true

        val expectedResponse = listOf(
            CountryResponse(
                name = CountryNameResponse(common = "Mexico", official = "United Mexican States"),
                capital = listOf("Mexico City"),
                flags = FlagsResponse(
                    png = "https://flagcdn.com/w320/mx.png",
                    svg = "https://flagcdn.com/mx.svg"
                )
            )
        )

        whenever(retrofit.create(CountriesService::class.java)).thenReturn(apiService)
        whenever(apiService.getCountryByName(countryName, fullText)).thenReturn(expectedResponse)

        // Act
        val result = countriesServiceImp.getCountryByName(countryName, fullText)

        // Assert
        assertEquals(expectedResponse, result)
        verify(retrofit).create(CountriesService::class.java)
        verify(apiService).getCountryByName(countryName, fullText)
    }
}