package com.mcwilliams.letscompose.network

import com.mcwilliams.letscompose.model.weatherdata.WeatherData
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {
    @GET("data/2.5/weather")
    suspend fun getLatLongByCity(
        @Query("lat") lat: String,
        @Query("lon") long: String,
        @Query("units") units: String = "Imperial",
        @Query("appid") key: String = "4e29ca8c4547fc74c003c8e58652d710"
    )
            : WeatherData

}