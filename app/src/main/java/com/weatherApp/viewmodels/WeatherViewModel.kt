package com.weatherApp.viewmodels

import androidx.lifecycle.*
import com.weatherApp.data.WeatherData
import com.weatherApp.data.repository.WeatherRepository
import com.weatherApp.utils.LoadingState
import kotlinx.coroutines.launch


class WeatherViewModel(private val weatherRepository: WeatherRepository) : BaseViewModel() {


   private val _weatherData = MutableLiveData<WeatherData>()
    val weatherData:LiveData<WeatherData>
    get() = _weatherData

    fun getWeatherData(city: String?,lat:Double?,lon:Double?) {

        viewModelScope.launch {

            try {
                _loadingState.value = LoadingState.LOADING
                _weatherData.value = weatherRepository.getWeatherData(city,lat,lon)
                _loadingState.value = LoadingState.LOADED
            } catch (e: Exception) {
                _loadingState.value = LoadingState.error(e.message)
            }

        }
    }










}