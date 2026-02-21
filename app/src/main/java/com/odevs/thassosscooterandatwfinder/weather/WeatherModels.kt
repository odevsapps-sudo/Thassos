package com.odevs.thassosscooterandatwfinder.weather

import com.google.gson.annotations.SerializedName

data class WeatherResponse(
    val weather: List<Weather>,
    val main: Main,
    val name: String
)

data class Weather(
    val id: Int,
    val main: String,
    val description: String,
    val icon: String
)

data class Main(
    val temp: Float
)

data class ForecastResponse(
    val list: List<ForecastItem>
)

data class ForecastItem(
    val dt: Long,
    val main: Main,
    val weather: List<Weather>,
    @SerializedName("dt_txt") val dtTxt: String
)