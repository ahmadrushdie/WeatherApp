package com.weatherApp.data

import com.google.gson.annotations.SerializedName
import java.text.SimpleDateFormat
import kotlin.collections.ArrayList

data class WeatherData(
    @SerializedName("data")
    var daysWeather:ArrayList<DayWeather>
)

data class DayWeather(
    @SerializedName("app_min_temp")
    var minTemp:Double,

    @SerializedName("app_max_temp")
    var maxTemp:Double,

    @SerializedName("weather")
    var weatherInfo:WeatherInfo,

    val datetime:String

){
    fun getFormattedDay():String
    {
        val format = SimpleDateFormat("yyyy-MM-dd")
        val date = format.parse(datetime)
        val dayFormatter = SimpleDateFormat("EEE")
        return dayFormatter.format(date)

    }

    fun getFormattedDate():String
    {
        val format = SimpleDateFormat("yyyy-MM-dd")
        val date = format.parse(datetime)
        val dayFormatter = SimpleDateFormat("dd/MM")
        return dayFormatter.format(date)

    }
}


data class WeatherInfo(
    var icon:String,
    var code:Int,
    var description:String
)