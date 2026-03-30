package com.luvsoft.countryapp.viewmodel

import android.content.Context
import androidx.annotation.CheckResult
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.luvsoft.core.ApiResponseStatus
import com.luvsoft.core.repositories.CountryRepository
import com.luvsoft.core.ui.Country
import com.luvsoft.rooom.network.api.CountryApi
import com.luvsoft.rooom.network.entities.CountryFavoriteEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class CountryViewModel @Inject constructor(
    private val repository: CountryRepository,
    private val database: CountryApi,
): AndroidViewModel()  {

    private val countriesToList = MutableLiveData<List<Country>>()

    val coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        throwable.printStackTrace()
    }

    private val _status = MutableLiveData<ApiResponseStatus<List<Country>>>()

    val status: LiveData<ApiResponseStatus<List<Country>>>
        get() = _status

    private val loading = MutableLiveData<Boolean>()

    private var error = MutableLiveData<Boolean>()

    fun getCountries() {
        viewModelScope.launch(Dispatchers.IO + coroutineExceptionHandler) {
            loading.postValue(true)
            val resultServer = repository.getAllCountries()
            handleResponseStatus(resultServer)
        }
    }

    fun getCountriesByName(name: String) {
        viewModelScope.launch(Dispatchers.IO + coroutineExceptionHandler) {
            val resultServer = repository.getCountryByName(name)
            handleResponseStatus(resultServer)
        }
    }

    private fun handleResponseStatus(downloadPs: ApiResponseStatus<List<Country>>) {

        if (downloadPs is ApiResponseStatus.Loading) {
            loading.postValue(true)
        }
        if (downloadPs is ApiResponseStatus.Success) {
            downloadPs.let {
                countriesToList.postValue(it.data)
                loading.postValue(false)
            }
        }
        _status.postValue(downloadPs)
    }

    suspend fun saveFavorite(country: Country) {
        val favorite = CountryFavoriteEntity(
            cca3 = country.cca3.toString(),
            commonName =  country.name.common?: "",
            officialName = country.name.official ?: "",
            capital = country.capital.joinToString(", "),
            flagUrl = country.flags?.png,
            coatOfArmsUrl = country.coatOfArms?.png,
            region = country.region,
            subregion = country.subregion,
            population = country.population,
            area = country.area
        )
        database.addFavorite(favorite) { id ->
            error.postValue(id >= 1)
        }
    }

    @CheckResult
    fun onCountriesFoundChange(): LiveData<List<Country>> = countriesToList

    @CheckResult
    fun onError(): LiveData<Boolean> = error

    @CheckResult
    fun onLoading(): LiveData<Boolean> = loading
}
