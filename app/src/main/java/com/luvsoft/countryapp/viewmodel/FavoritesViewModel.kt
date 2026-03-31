package com.luvsoft.countryapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.luvsoft.rooom.network.api.CountryApi
import com.luvsoft.rooom.network.entities.CountryFavoriteEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class FavoritesViewModel @Inject constructor(
    private val database: CountryApi,
) : AndroidViewModel() {

    private val countriesFavorites = MutableLiveData<List<CountryFavoriteEntity>>()
    private val loading = MutableLiveData<Boolean>()

    private val _updateResult = MutableLiveData<Boolean>()
    val updateResult: LiveData<Boolean> = _updateResult

    private val _updateError = MutableLiveData<Throwable?>()
    val updateError: LiveData<Throwable?> = _updateError

    val coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        throwable.printStackTrace()
    }

    fun getCountriesFavorites() {
        loading.value = true
        viewModelScope.launch(coroutineExceptionHandler) {
            database.getAllFavorites { list ->
                countriesFavorites.postValue(list)
                loading.postValue(false)
            }
        }
    }

    fun deleteFavorite(item: CountryFavoriteEntity) {
        viewModelScope.launch(coroutineExceptionHandler) {
            try {
                val rows = database.deleteFavorite(item)
                _updateResult.postValue(rows > 0)
            } catch (e: Exception) {
                _updateError.postValue(e)
                _updateResult.postValue(false)
            }
        }
    }

    fun onCountriesFavorites(): LiveData<List<CountryFavoriteEntity>> = countriesFavorites
    fun onLoading(): LiveData<Boolean> = loading
}
