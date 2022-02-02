package com.weatherApp.data.api

import com.weatherApp.data.WeatherData
import retrofit2.http.GET
import retrofit2.http.Query


interface WeatherApi {

    @GET("forecast/daily")
    suspend fun getWeather(
        @Query("key") key: String,
        @Query("city") city: String?,
        @Query("lat") lat: Double?,
        @Query("lon") lon: Double?,
        @Query("days") days: Int = 16

                           ):WeatherData

}