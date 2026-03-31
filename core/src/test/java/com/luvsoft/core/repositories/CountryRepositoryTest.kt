package com.luvsoft.core.repositories

import com.luvsoft.core.ApiResponseStatus
import com.luvsoft.core.network.models.CountryNameResponse
import com.luvsoft.core.network.models.CountryResponse
import com.luvsoft.core.network.models.FlagsResponse
import com.luvsoft.core.network.services.CountriesService
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

class CountryRepositoryTest {

    private lateinit var apiService: CountriesService
    private lateinit var repository: CountryRepository

    @Before
    fun setUp() {
        apiService = mock()
        repository = CountryRepository(apiService)
    }

    @Test
    fun `getAllCountries should return success with mapped country list`() = runTest {
        val apiResponse = listOf(
            CountryResponse(
                name = CountryNameResponse(
                    common = "Mexico",
                    official = "United Mexican States"
                ),
                capital = listOf("Mexico City"),
                flags = FlagsResponse(
                    png = "https://flagcdn.com/w320/mx.png",
                    svg = "https://flagcdn.com/mx.svg"
                )
            )
        )

        whenever(apiService.getAllCountries("name,capital,flags")).thenReturn(apiResponse)

        val result = repository.getAllCountries()

        when (result) {
            is ApiResponseStatus.Success -> {
                assertEquals(1, result.data.size)
            }
            is ApiResponseStatus.Error -> {
                throw AssertionError("Se esperaba Success pero fue Error")
            }
            is ApiResponseStatus.Loading -> {
                throw AssertionError("No se esperaba Loading")
            }
        }

        verify(apiService).getAllCountries("name,capital,flags")
    }

    @Test
    fun `getCountryByName should return success with mapped country list`() = runTest {
        val countryName = "Mexico"

        val apiResponse = listOf(
            CountryResponse(
                name = CountryNameResponse(
                    common = "Mexico",
                    official = "United Mexican States"
                ),
                capital = listOf("Mexico City"),
                flags = FlagsResponse(
                    png = "https://flagcdn.com/w320/mx.png",
                    svg = "https://flagcdn.com/mx.svg"
                )
            )
        )

        whenever(apiService.getCountryByName(countryName)).thenReturn(apiResponse)

        val result = repository.getCountryByName(countryName)

        when (result) {
            is ApiResponseStatus.Success -> {
                assertEquals(1, result.data.size)
            }
            is ApiResponseStatus.Error -> {
                throw AssertionError("Se esperaba Success pero fue Error")
            }
            is ApiResponseStatus.Loading -> {
                throw AssertionError("No se esperaba Loading")
            }
        }

        verify(apiService).getCountryByName(countryName)
    }
}