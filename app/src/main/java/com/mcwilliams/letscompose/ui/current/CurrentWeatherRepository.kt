package com.mcwilliams.letscompose.ui.current

import android.content.Context
import android.content.SharedPreferences
import com.mcwilliams.letscompose.R
import com.mcwilliams.letscompose.model.weatherdata.WeatherData
import com.mcwilliams.letscompose.network.LocationApi
import com.mcwilliams.letscompose.network.WeatherApi
import javax.inject.Inject

class CurrentWeatherRepository @Inject constructor(
    context: Context,
    private val weatherApi: WeatherApi,
    private val locationApi: LocationApi
) {

    private val preferences: SharedPreferences = context.getSharedPreferences(
        context.getString(R.string.preference_file_key),
        Context.MODE_PRIVATE
    )

    suspend fun getWeather(search: String): WeatherData {
        val cityDataByLatLong = locationApi.getLatLongByCity(search)
        return weatherApi.getLatLongByCity(
            cityDataByLatLong.results[0].geometry.lat.toString(),
            cityDataByLatLong.results[0].geometry.lng.toString()
        )
    }
}