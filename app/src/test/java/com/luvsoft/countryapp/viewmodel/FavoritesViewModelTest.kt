package com.luvsoft.countryapp.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.luvsoft.rooom.network.api.CountryApi
import com.luvsoft.rooom.network.entities.CountryFavoriteEntity
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Rule
import org.junit.rules.TestWatcher
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.any
import org.mockito.kotlin.doAnswer
import org.mockito.kotlin.doThrow
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import kotlin.test.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)

class FavoritesViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var apiRoom: CountryApi

    private lateinit var viewmodel: FavoritesViewModel

    private lateinit var favorite: CountryFavoriteEntity

    @Mock
    lateinit var loadingObserver: Observer<Boolean>

    @Mock
    lateinit var countriesObserver: Observer<List<CountryFavoriteEntity>>

    @Mock
    lateinit var updateResultObserver: Observer<Boolean>

    @Mock
    lateinit var updateErrorObserver: Observer<Throwable?>

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        apiRoom = mock()
        init()
        initMock()
    }

    private fun init() {
        viewmodel = FavoritesViewModel(apiRoom)
        viewmodel.onCountriesFavorites().observeForever(countriesObserver)
        viewmodel.onLoading().observeForever(loadingObserver)
        viewmodel.updateResult.observeForever(updateResultObserver)
        viewmodel.updateError.observeForever(updateErrorObserver)
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
    fun `getCountriesFavorites should update favorites list and loading state`() = runTest {
        val favorites = listOf(favorite)

        doAnswer { invocation ->
            val callback = invocation.getArgument<(List<CountryFavoriteEntity>) -> Unit>(0)
            callback(favorites)
        }.whenever(apiRoom).getAllFavorites(any())

        viewmodel.getCountriesFavorites()
        advanceUntilIdle()

        assertEquals(favorites, viewmodel.onCountriesFavorites().value)
        assertFalse(viewmodel.onLoading().value ?: true)

        verify(apiRoom).getAllFavorites(any())
        verify(loadingObserver).onChanged(true)
        verify(countriesObserver).onChanged(favorites)
        verify(loadingObserver).onChanged(false)
    }

    @Test
    fun `deleteFavorite should update result true when delete is successful`() = runTest {
        whenever(apiRoom.deleteFavorite(favorite)).thenReturn(1)

        viewmodel.deleteFavorite(favorite)
        advanceUntilIdle()

        assertTrue(viewmodel.updateResult.value == true)
        assertEquals(null, viewmodel.updateError.value)

        verify(apiRoom).deleteFavorite(favorite)
        verify(updateResultObserver).onChanged(true)
    }

    @Test
    fun `deleteFavorite should update result false and error when delete fails`() = runTest {
        val exception = RuntimeException("delete error")
        doThrow(exception).whenever(apiRoom).deleteFavorite(favorite)

        viewmodel.deleteFavorite(favorite)
        advanceUntilIdle()

        assertFalse(viewmodel.updateResult.value ?: true)
        assertEquals(exception, viewmodel.updateError.value)

        verify(apiRoom).deleteFavorite(favorite)
        verify(updateResultObserver).onChanged(false)
        verify(updateErrorObserver).onChanged(exception)
    }
}

@OptIn(ExperimentalCoroutinesApi::class)
class MainDispatcherRule(
    private val dispatcher: TestDispatcher = StandardTestDispatcher()
) : TestWatcher() {

    override fun starting(description: org.junit.runner.Description) {
        Dispatchers.setMain(dispatcher)
    }

    override fun finished(description: org.junit.runner.Description) {
        Dispatchers.resetMain()
    }
}
