package com.odevs.thassosscooterandatwfinder.weather

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.airbnb.lottie.compose.*

@Composable
fun WeatherWidget(
    modifier: Modifier = Modifier,
    city: String = "Thassos",
    apiKey: String,
    viewModel: WeatherViewModel = viewModel()
) {
    LaunchedEffect(Unit) {
        viewModel.fetchWeather(city, apiKey)
        viewModel.fetchForecast(city, apiKey)
    }

    val weatherInfo by viewModel.weatherInfo.collectAsState()
    val tomorrowForecast by viewModel.tomorrowForecast.collectAsState()
    val seaBlue = Color(0xFFB4E0E6)

    weatherInfo?.let { current ->
        val iconCode = current.weather.firstOrNull()?.icon ?: ""
        val temperature = current.main.temp
        val description = current.weather.firstOrNull()?.description ?: ""
        val iconUrl = "https://openweathermap.org/img/wn/${iconCode}@2x.png"

        Column(
            modifier = modifier
                .shadow(6.dp, shape = RoundedCornerShape(16.dp))
                .background(seaBlue, shape = RoundedCornerShape(16.dp))
                .padding(16.dp)
                .widthIn(min = 160.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("🌤️ Today", fontWeight = FontWeight.Bold)

            AsyncImage(
                model = iconUrl,
                contentDescription = description,
                modifier = Modifier.size(48.dp)
            )

            Text(
                text = "${temperature}°C",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = description.replaceFirstChar { it.uppercase() },
                style = MaterialTheme.typography.bodyMedium,
                color = Color.DarkGray
            )

            Spacer(modifier = Modifier.height(12.dp))

            tomorrowForecast?.let { forecast ->
                val tIcon = forecast.weather.firstOrNull()?.icon ?: ""
                val tDesc = forecast.weather.firstOrNull()?.description ?: ""
                val tTemp = forecast.main.temp
                val tIconUrl = "https://openweathermap.org/img/wn/${tIcon}@2x.png"

                Text("🌥️ Tomorrow", fontWeight = FontWeight.Bold)
                AsyncImage(
                    model = tIconUrl,
                    contentDescription = tDesc,
                    modifier = Modifier.size(40.dp)
                )
                Text(
                    text = "${tTemp}°C",
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Medium
                )
                Text(
                    text = tDesc.replaceFirstChar { it.uppercase() },
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.DarkGray
                )
            }
        }
    }
}

@Composable
fun AnimatedWeatherIcon(iconType: String) {
    val animationUrl = when (iconType) {
        "01d" -> "https://lottie.host/1df77e80-35e8-4d17-8977-cf3a7784a2d8/ZqfIsGBCs5.json"
        "02d" -> "https://lottie.host/6b3c7e56-404a-407e-b8f1-2eaeb43c52b5/1VYpg9tTb9.json"
        "09d", "10d" -> "https://lottie.host/b2334d61-82c1-4f15-bd7d-e47d3190bcbc/bv1apTzhcz.json"
        else -> "https://lottie.host/1df77e80-35e8-4d17-8977-cf3a7784a2d8/ZqfIsGBCs5.json"
    }

    val composition by rememberLottieComposition(LottieCompositionSpec.Url(animationUrl))
    val progress by animateLottieCompositionAsState(
        composition,
        iterations = LottieConstants.IterateForever
    )

    LottieAnimation(
        composition = composition,
        progress = { progress },
        modifier = Modifier.size(64.dp)
    )
}
