package com.weatherApp.features.weatherhome.viewholders

import androidx.recyclerview.widget.RecyclerView
import com.weatherApp.R
import com.weatherApp.constants.IMAGES_BASE_URL
import com.weatherApp.databinding.ItemDayViewBinding
import com.weatherApp.data.DayWeather
import com.weatherApp.extentions.showImageFromUrl

class DayWeatherViewHolder(val binding: ItemDayViewBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(dayWeather: DayWeather){
        var url = "$IMAGES_BASE_URL${dayWeather.weatherInfo.icon}.png"
        binding.ivWeatherIcon.showImageFromUrl(url)
        binding.tvDayName.text = dayWeather.getFormattedDay()
        binding.tvDate.text = dayWeather.getFormattedDate()
        binding.tvWeatherDegree.text = binding.root.context.getString(R.string.temp_value,dayWeather.maxTemp)
    }
}
