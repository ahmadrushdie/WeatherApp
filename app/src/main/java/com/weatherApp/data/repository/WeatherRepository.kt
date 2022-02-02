package com.weatherApp.data.repository

import com.weatherApp.constants.API_KEY
import com.weatherApp.data.api.WeatherApi

class WeatherRepository(private val weatherApi: WeatherApi) {

    suspend fun getWeatherData(city: String?, lat: Double?, lon: Double?) =
        weatherApi.getWeather(API_KEY, city, lat, lon)
}