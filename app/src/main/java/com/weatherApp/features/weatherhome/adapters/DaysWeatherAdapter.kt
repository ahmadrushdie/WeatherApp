package com.weatherApp.features.weatherhome.adapters;

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.weatherApp.databinding.ItemDayViewBinding
import com.weatherApp.data.DayWeather
import com.weatherApp.features.weatherhome.viewholders.DayWeatherViewHolder
import java.util.ArrayList


class DaysWeatherAdapter( var daysWeather:ArrayList<DayWeather>) :
    RecyclerView.Adapter<DayWeatherViewHolder>() {
    lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DayWeatherViewHolder {
        this.context = parent.context

          return  DayWeatherViewHolder(
               ItemDayViewBinding.inflate(LayoutInflater.from(context), parent, false)
            )
    }



    override fun getItemCount(): Int {
        return daysWeather.size
    }

    override fun onBindViewHolder(holder: DayWeatherViewHolder, position: Int) {
        var item = daysWeather[position]
        holder.bind(item)

    }


}