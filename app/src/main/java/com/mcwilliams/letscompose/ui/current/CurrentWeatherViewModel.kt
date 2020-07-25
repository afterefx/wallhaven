package com.mcwilliams.letscompose.ui.current

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mcwilliams.letscompose.model.allweatherdata.WeatherData
import kotlinx.coroutines.launch

class CurrentWeatherViewModel @ViewModelInject constructor(
    private val currentWeatherRepository: CurrentWeatherRepository
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
            _weatherData.postValue(
                currentWeatherRepository.getWeather(search = search)
            )
        }
    }

}