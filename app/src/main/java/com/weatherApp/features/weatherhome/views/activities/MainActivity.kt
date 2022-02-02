package com.weatherApp.features.weatherhome.views.activities

import android.graphics.Color
import android.location.Location
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.WindowManager
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.weatherApp.R
import com.weatherApp.databinding.ActivityMainBinding
import com.weatherApp.data.DayWeather
import com.weatherApp.data.WeatherData
import com.weatherApp.utils.LoadingState
import com.weatherApp.viewmodels.WeatherViewModel
import com.weatherApp.features.weatherhome.adapters.DaysWeatherAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel
import android.view.inputmethod.EditorInfo

import android.widget.TextView.OnEditorActionListener
import androidx.annotation.RequiresApi
import com.weatherApp.extentions.*


class MainActivity : BaseActivity() {
    private val binding by viewBinding(ActivityMainBinding::inflate)
    private val weatherViewModel:WeatherViewModel by viewModel()


    override fun onLocationChanged(location: Location) {
        binding.include.tvLatLon.show()
        binding.include.tvLatLon.text="${location.latitude} - ${location.longitude}"
        weatherViewModel.getWeatherData(null,location.latitude,location.longitude)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        forceTransparentStatus()
        initLocation()
        initObservables()
        initListeners()

        weatherViewModel.getWeatherData(binding.include.etSearchCountry.text.toString(),null,null)


    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun forceTransparentStatus() {
        window.apply {
            clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            statusBarColor = Color.TRANSPARENT
        }
    }

    private fun initListeners() {

        binding.include.etSearchCountry.setOnEditorActionListener(OnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                var searchText = binding.include.etSearchCountry.text.toString()
                if(!searchText.isNullOrEmpty())
                {
                    binding.include.tvLatLon.gone()
                    weatherViewModel.getWeatherData(searchText,null,null)
                }else{
                    toast("Please Enter City Name")
                }

                true
            } else false
        })

        binding.include.ivLocation.setOnClickListener {
            binding.include.etSearchCountry.text = null
            getLocation()
        }

    }

    private fun initObservables() {
        weatherViewModel.weatherData.observe(this, Observer(::onWeatherInfoReceived))
        weatherViewModel.loadingState.observe(this, Observer(::onLoadingStateReceived))

    }

    private fun onLoadingStateReceived(loadingState: LoadingState) {
        when (loadingState){
           LoadingState.LOADING ->{
               binding.include.pbLoadWeatherInfo.showView(true)
           }
            LoadingState.LOADED ->{
                binding.include.pbLoadWeatherInfo.showView(false)
            }

            else->{
                showNoData()
               // Toast.makeText(applicationContext,loadingState.msg,Toast.LENGTH_SHORT).show()
            }





        }

    }

    private fun showNoData() {
        binding.include.tvTodayWeatherDesc.gone()
        binding.include.tvTodayTemp.gone()
        binding.include.rcWeather.gone()
        binding.include.pbLoadWeatherInfo.gone()
        binding.include.tvNoDataLabel.show()
    }

    private fun onWeatherInfoReceived(weatherData: WeatherData) {

        initTodayWeather(weatherData.daysWeather[0])
        initRecycleViewWithData(weatherData)
    }

    private fun initTodayWeather(dayWeather: DayWeather) {
        binding.include.tvTodayWeatherDesc.show()
        binding.include.tvTodayTemp.show()
        binding.include.tvTodayTemp.text = getString(R.string.temp_value,dayWeather.maxTemp)
        binding.include.tvTodayWeatherDesc.text = dayWeather.weatherInfo.description
    }

    private fun initRecycleViewWithData(weatherData: WeatherData) {
        binding.include.rcWeather.show()
        binding.include.tvNoDataLabel.gone()

        binding.include.rcWeather.apply {
            layoutManager = LinearLayoutManager(applicationContext, LinearLayoutManager.HORIZONTAL, false);
            adapter = DaysWeatherAdapter(weatherData.daysWeather)
        }
    }







    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }
}
