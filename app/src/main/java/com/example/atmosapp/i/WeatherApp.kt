package com.example.atmosapp.i

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.atmosapp.models.Clouds
import com.example.atmosapp.models.Coord
import com.example.atmosapp.models.Main
import com.example.atmosapp.models.Sys
import com.example.atmosapp.models.Weather
import com.example.atmosapp.models.WeatherResponse
import com.example.atmosapp.models.Wind
import com.example.atmosapp.viewmodel.WeatherViewModel

// Assuming your data classes are defined as shown before
@Composable
fun WeatherScreen(viewModel: WeatherViewModel) {
    // Observe the LiveData object from the ViewModel
    val weatherResponse: WeatherResponse? = viewModel.weatherData.value

    // Now 'weatherResponse' holds the actual WeatherResponse object or null
    weatherResponse?.let {
        // Pass the WeatherResponse object to the composable that needs it
        WeatherApp(it)
    }
}
@Composable
fun WeatherApp(weather: WeatherResponse) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = "City: ${weather.name}")
        Text(text = "Temperature: ${weather.main.temp} K")
        Text(text = "Weather: ${weather.weather.first().main} - ${weather.weather.first().description}")
        Text(text = "Humidity: ${weather.main.humidity}%")
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewWeatherApp() {
    // Example data for preview
    val exampleWeather = WeatherResponse(
        coord = Coord(-80.8431, 35.2271),
        weather = listOf(Weather(800, "Clear", "clear sky", "01n")),
        main = Main(287.91, 286.15, 286.88, 289.13, 1017, 27),
        visibility = 10000,
        wind = Wind(2.57, 320),
        clouds = Clouds(0),
        dt = 1711672956,
        sys = Sys(2, 2007844, "US", 1711624493, 1711669284),
        timezone = -14400,
        id = 4460243,
        name = "Charlotte",
        cod = 200
    )

    WeatherApp(exampleWeather)
}
