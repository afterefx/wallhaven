package com.mcwilliams.letscompose.network

import com.mcwilliams.letscompose.model.citydata.CityDataByLatLong
import retrofit2.http.GET
import retrofit2.http.Query


interface LocationApi {
    @GET("geocode/v1/json")
    suspend fun getLatLongByCity(@Query("q") city: String, @Query("key") key : String = "d78d8fde6f8f4c2ba6982fd234b352b5"): CityDataByLatLong
}