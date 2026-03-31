@file:OptIn(kotlinx.coroutines.ExperimentalCoroutinesApi::class)

package com.luvsoft.countryapp.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.luvsoft.base.extensions.getOrAwaitValue
import com.luvsoft.core.ApiResponseStatus
import com.luvsoft.core.repositories.CountryRepository
import com.luvsoft.core.ui.CoatOfArms
import com.luvsoft.core.ui.Country
import com.luvsoft.core.ui.CountryFlags
import com.luvsoft.core.ui.CountryName
import com.luvsoft.rooom.network.api.CountryApi
import com.luvsoft.rooom.network.entities.CountryFavoriteEntity
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.any
import org.mockito.kotlin.doAnswer
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

class CountryViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var apiRoom: CountryApi

    private lateinit var repository: CountryRepository

    private lateinit var viewmodel: CountryViewModel

    private lateinit var favorite: CountryFavoriteEntity

    private lateinit var country: Country

    @Mock
    lateinit var countriesToList: Observer<List<Country>>

    @Mock
    lateinit var error: Observer<Boolean>

    @Mock
    lateinit var load: Observer<Boolean>

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        apiRoom = mock()
        repository = mock()
        init()
        initMock()
    }

    private fun init() {
        viewmodel = CountryViewModel(repository, apiRoom)
        viewmodel.onCountriesFoundChange().observeForever(countriesToList)
        viewmodel.onError().observeForever(error)
        viewmodel.onLoading().observeForever(load)
    }

    private fun initMock() {
        favorite = CountryFavoriteEntity(
            cca3 = "MEX",
            commonName = "Mexico",
            officialName = "Estados Unidos Mexicanos",
            capital = "Ciudad de México",
            flagUrl = "flag_url",
            coatOfArmsUrl = "coat_url",
            region = "Americas",
            subregion = "North America",
            population = 1,
            area = 1.5
        )

        country = Country(
            name = CountryName(
                common = "Mexico",
                official = "Estados Unidos Mexicanos"
            ),
            cca3 = "MEX",
            capital = listOf("Ciudad de México"),
            region = "Americas",
            subregion = "North America",
            population = 1000,
            area = 2000.0,
            flags = CountryFlags(
                png = "flag_url",
                svg = "flag_svg"
            ),
            coatOfArms = CoatOfArms(
                png = "coat_url",
                svg = "coat_svg"
            )
        )
    }

    @Test
    fun `getCountries should emit success and update countries list`() = runTest {
        val expectedCountries = listOf(country)
        val response = ApiResponseStatus.Success(expectedCountries)

        whenever(repository.getAllCountries()).thenReturn(response)

        viewmodel.getCountries()

        val result = viewmodel.onCountriesFoundChange().getOrAwaitValue()
        val status = viewmodel.status.getOrAwaitValue()

        assertEquals(expectedCountries, result)
        assertTrue(status is ApiResponseStatus.Success)
        assertEquals(false, viewmodel.onLoading().getOrAwaitValue())

        verify(repository).getAllCountries()
    }

    @Test
    fun `getCountriesByName should emit success and update countries list`() = runTest {
        val expectedCountries = listOf(country)
        val response = ApiResponseStatus.Success(expectedCountries)

        whenever(repository.getCountryByName("Mexico")).thenReturn(response)

        viewmodel.getCountriesByName("Mexico")

        val result = viewmodel.onCountriesFoundChange().getOrAwaitValue()
        val status = viewmodel.status.getOrAwaitValue()

        assertEquals(expectedCountries, result)
        assertTrue(status is ApiResponseStatus.Success)
        verify(repository).getCountryByName("Mexico")
    }

    @Test
    fun `saveFavorite should insert favorite and update error liveData as true`() = runTest {
        doAnswer { invocation ->
            val callback = invocation.getArgument<(Long) -> Unit>(1)
            callback(1L)
            Unit
        }.whenever(apiRoom).addFavorite(any(), any())

        viewmodel.saveFavorite(country)

        val result = viewmodel.onError().getOrAwaitValue()

        assertTrue(result)

        verify(apiRoom).addFavorite(any(), any())
    }
}
