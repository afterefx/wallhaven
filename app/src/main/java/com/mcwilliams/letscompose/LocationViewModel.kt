package com.mcwilliams.letscompose

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mcwilliams.letscompose.model.citydata.CityDataByLatLong
import com.mcwilliams.letscompose.model.weatherdata.WeatherData
import com.mcwilliams.letscompose.network.LocationApi
import com.mcwilliams.letscompose.network.WeatherApi
import kotlinx.coroutines.launch

class LocationViewModel @ViewModelInject constructor(
    private val locationApi: LocationApi,
    private val weatherApi: WeatherApi
) : ViewModel() {

    var _weatherData = MutableLiveData<WeatherData>()
    var weatherData: LiveData<WeatherData> = _weatherData

    init {
        getWeatherData("Boerne")
    }

    fun search(text: String) {
        viewModelScope.launch {
            getWeatherData(text)
        }
    }

    fun getWeatherData(search: String) {
        viewModelScope.launch {
            val cityDataByLatLong = locationApi.getLatLongByCity(search)
            _weatherData.postValue(
                weatherApi.getLatLongByCity(
                    cityDataByLatLong.results[0].geometry.lat.toString(),
                    cityDataByLatLong.results[0].geometry.lng.toString()
                )
            )
        }
    }

}