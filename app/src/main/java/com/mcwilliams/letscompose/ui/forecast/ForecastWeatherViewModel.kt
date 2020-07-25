package com.mcwilliams.letscompose.ui.forecast

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

class ForecastWeatherViewModel @ViewModelInject constructor(
    private val locationApi: LocationApi,
    private val weatherApi: WeatherApi
) : ViewModel() {


}