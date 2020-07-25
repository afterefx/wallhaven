package com.mcwilliams.letscompose.ui.current

import android.content.Context
import android.content.SharedPreferences
import com.mcwilliams.letscompose.R
import com.mcwilliams.letscompose.model.weatherdata.WeatherData
import com.mcwilliams.letscompose.network.LocationApi
import com.mcwilliams.letscompose.network.WeatherApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
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
        val locations = preferences.getStringSet("locations", mutableSetOf())
        locations!!.add(search)
        preferences.edit().putStringSet("locations", locations).apply()

        val zipOrCity = search.intOrString()
        return if (zipOrCity is Int) {
            weatherApi.getWeatherDataByZipCode(zipOrCity.toString())
        } else
            weatherApi.getWeatherDataByCity((zipOrCity as String))
    }

    fun String.intOrString() = toIntOrNull() ?: this
}