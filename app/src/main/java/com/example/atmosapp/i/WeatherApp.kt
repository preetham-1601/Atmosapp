package com.example.atmosapp.i

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.atmosapp.models.Clouds
import com.example.atmosapp.models.Coord
import com.example.atmosapp.models.Main
import com.example.atmosapp.models.Sys
import com.example.atmosapp.models.Weather
import com.example.atmosapp.models.WeatherResponse
import com.example.atmosapp.models.Wind
import com.example.atmosapp.network.RetrofitClient
import com.example.atmosapp.repository.WeatherRepository
import com.example.atmosapp.viewmodel.WeatherViewModel
import com.example.atmosapp.viewmodel.WeatherViewModelFactory

// Assuming your data classes are defined as shown before
@Composable
fun WeatherScreen() {

    val viewModel: WeatherViewModel = viewModel(factory = WeatherViewModelFactory())
    val weather = viewModel.weatherData.value

    LaunchedEffect(true) {
        viewModel.loadWeather("Charlotte")
    }

    // Dummy content for now, actual data should come from ViewModel
    Column(modifier = Modifier.padding(16.dp)) {
        if (weather != null) {
            Text(text = "City: ${weather.name}")
            Text(text = "Temperature: ${weather.main.temp} K")
            Text(text = "Weather: ${weather.weather.first().description}")
            Text(text = "Humidity: ${weather.main.humidity}%")
        } else {
            Text(text = "Loading weather data...")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewWeatherApp() {
    WeatherScreen()
}