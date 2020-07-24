package com.mcwilliams.letscompose.model.weatherdata

data class Sys(
    val country: String,
    val id: Int,
    val sunrise: Int,
    val sunset: Int,
    val type: Int
)