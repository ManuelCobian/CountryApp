package com.luvsoft.rooom.api

import com.luvsoft.rooom.network.api.CountryApi
import com.luvsoft.rooom.network.api.imp.CountryImp
import com.luvsoft.rooom.network.entities.CountryFavoriteEntity
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.eq
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

class CountryApiTest {

    private lateinit var api: CountryApi
    private lateinit var apiImp: CountryImp
    private lateinit var countryDoo: CountryDoo

    private lateinit var favorite: CountryFavoriteEntity

    @Before
    fun setUp() {
        api = mock()
        countryDoo = mock()
        apiImp = CountryImp(countryDoo)
        initMock()
    }

    private fun initMock() {
        favorite = CountryFavoriteEntity(
            cca3 = "",
            commonName = "",
            officialName = "",
            capital = "",
            flagUrl = "",
            coatOfArmsUrl = "",
            region = "",
            subregion = "",
            population = 1,
            area = 1.5
        )
    }

    @Test
    fun `getAllFavorites should call dao and return favorites in callback`() = runTest {
        val favorites = listOf(favorite)
        var callbackResult: List<CountryFavoriteEntity> = emptyList()

        whenever(countryDoo.getAllFavorites()).thenReturn(favorites)

        apiImp.getAllFavorites { result ->
            callbackResult = result
        }

        assertEquals(favorites, callbackResult)
        verify(countryDoo).getAllFavorites()
    }

    @Test
    fun `addFavorite should call dao insertFavorite and return inserted id in callback`() = runTest {
        val insertedId = 1L
        var callbackResult = 0L

        whenever(countryDoo.insertFavorite(favorite)).thenReturn(insertedId)

        apiImp.addFavorite(favorite) { result ->
            callbackResult = result
        }

        assertEquals(insertedId, callbackResult)
        verify(countryDoo).insertFavorite(eq(favorite))
    }

    @Test
    fun `deleteFavorite should call dao deleteFavorite and return deleted rows`() = runTest {
        val deletedRows = 1

        whenever(countryDoo.deleteFavorite(favorite)).thenReturn(deletedRows)

        val result = apiImp.deleteFavorite(favorite)

        assertEquals(deletedRows, result)
        verify(countryDoo).deleteFavorite(eq(favorite))
    }
}