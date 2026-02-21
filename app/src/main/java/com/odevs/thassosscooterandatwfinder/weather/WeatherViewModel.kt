package com.odevs.thassosscooterandatwfinder.weather

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import com.google.gson.annotations.SerializedName

class WeatherViewModel : ViewModel() {

    private val _weatherInfo = MutableStateFlow<WeatherResponse?>(null)
    val weatherInfo: StateFlow<WeatherResponse?> = _weatherInfo

    private val _tomorrowForecast = MutableStateFlow<ForecastItem?>(null)
    val tomorrowForecast: StateFlow<ForecastItem?> = _tomorrowForecast

    fun fetchWeather(city: String, apiKey: String) {
        viewModelScope.launch {
            try {
                val response = WeatherApiClient.apiService.getCurrentWeather(
                    cityName = city,
                    apiKey = apiKey,
                    units = "metric"
                )
                _weatherInfo.value = response
            } catch (e: Exception) {
                e.printStackTrace()
                _weatherInfo.value = null
            }
        }
    }

    fun fetchForecast(city: String, apiKey: String) {
        viewModelScope.launch {
            try {
                val response = WeatherApiClient.apiService.getForecast(
                    cityName = city,
                    apiKey = apiKey,
                    units = "metric"
                )

                val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                val tomorrow = Calendar.getInstance().apply { add(Calendar.DAY_OF_YEAR, 1) }
                val tomorrowStr = dateFormat.format(tomorrow.time)

                // Keresünk egy holnapi 12:00-ás előrejelzést
                val forecastForTomorrowNoon = response.list.firstOrNull {
                    it.dtTxt.startsWith(tomorrowStr) && it.dtTxt.contains("12:00")
                }

                _tomorrowForecast.value = forecastForTomorrowNoon

            } catch (e: Exception) {
                e.printStackTrace()
                _tomorrowForecast.value = null
            }
        }
    }
}
