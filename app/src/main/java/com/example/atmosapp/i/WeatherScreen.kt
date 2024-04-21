package com.example.atmosapp.i


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding

import androidx.compose.foundation.rememberScrollState

import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.material3.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.atmosapp.models.WeatherResponse
import com.example.atmosapp.viewmodel.WeatherViewModel
import com.example.atmosapp.viewmodel.WeatherViewModelFactory
import kotlin.math.roundToInt

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun WeatherScreen() {
    val viewModel: WeatherViewModel = viewModel(factory = WeatherViewModelFactory())
    val weather = viewModel.weatherData.value
    var searchText by remember { mutableStateOf("") }
    var active by remember { mutableStateOf(false) }
    var hasSearched by remember { mutableStateOf(false) } // Tracks if search has been initiated
    val keyboardController = LocalSoftwareKeyboardController.current
    val topBarColor = MaterialTheme.colorScheme.primaryContainer
    var selectedUnit by remember { mutableStateOf("Celsius") }

    val isLoading = viewModel.isLoading.value
    val errorMessage = viewModel.errorMessage.value



    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Weather App") },
                actions = {
                    IconButton(onClick = { active = !active }) {
                        Icon(Icons.Default.Search, contentDescription = "Search")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = topBarColor
                )
            )
        }
    ) { innerPadding ->
        Column(modifier = Modifier
            .padding(innerPadding)
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Center,  // Vertically center the contents
        horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (active) {
                TextField(
                    value = searchText,
                    onValueChange = { searchText = it },
                    placeholder = { Text("Search City") },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                    keyboardActions = KeyboardActions(onSearch = {
                        viewModel.loadWeather(searchText)
                        active = false
                        hasSearched = true
                        keyboardController?.hide()
                    })
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            if (isLoading) {
                Text("Loading weather data...")
            } else if (!errorMessage.isEmpty()) {
                Text(errorMessage, color = Color.Red)
            } else if (weather != null) {
                Text(
                    text = weather.name,
                    style = MaterialTheme.typography.displayMedium,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                TemperatureCard(weather = weather, selectedUnit = selectedUnit, onUnitChanged = { selectedUnit = it })
                Spacer(modifier = Modifier.height(16.dp))
                WeatherCard(weather = weather)
                Spacer(modifier = Modifier.height(16.dp))
                HumidityCard(weather = weather)
                Spacer(modifier = Modifier.height(16.dp))
                WindSpeedCard(weather = weather)
                Spacer(modifier = Modifier.height(16.dp))
            } else {
                Text("Search for your city to display weather data.")
            }

        }
    }
}
@Composable
fun TemperatureCard(weather: WeatherResponse, selectedUnit: String, onUnitChanged: (String) -> Unit) {
    val units = listOf("Celsius", "Fahrenheit", "Kelvin")
    var selectedOption by remember { mutableStateOf(selectedUnit) }
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Temperature display
            Text(
                text = convertTemperature(weather.main.temp, selectedOption),
                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                modifier = Modifier.weight(1f)
            )

            // Radio buttons for selecting temperature unit
            Column(modifier = Modifier.weight(1f)) {
                units.forEach { unit ->
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        RadioButton(
                            selected = selectedOption == unit,
                            onClick = {
                                selectedOption = unit
                                onUnitChanged(unit)
                            }
                        )
                        Text(unit, style = MaterialTheme.typography.bodyLarge)
                    }
                }
            }
        }
        Row(
            modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
        ){Text("Low: ", style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.Bold)
            Text((convertTemperature(weather.main.temp_min, selectedOption)), style = MaterialTheme.typography.bodyLarge)
            Spacer(modifier = Modifier.weight(1f))
            Text("High: ", style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.Bold)
            Text((convertTemperature(weather.main.temp_max, selectedOption)), style = MaterialTheme.typography.bodyLarge)
        }
        }


}

@Composable
fun WeatherCard(weather: WeatherResponse) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Weather Condition", style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.Bold)
            Text(weather.weather.first().description, style = MaterialTheme.typography.bodyLarge)
        }
    }
}

@Composable
fun HumidityCard(weather: WeatherResponse) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Humidity", style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.Bold)
            Text("${weather.main.humidity}%", style = MaterialTheme.typography.bodyLarge)
        }
    }
}@Composable
fun WindSpeedCard(weather: WeatherResponse) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Wind Speed", style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.Bold)
            Text("${weather.wind.speed} MPH", style = MaterialTheme.typography.bodyLarge)

        }
    }
}


fun convertTemperature(tempK: Double, unit: String): String = when (unit) {
    "Celsius" -> "${(tempK - 273.15).roundToInt()}°C"
    "Fahrenheit" -> "${((tempK - 273.15) * 9/5 + 32).roundToInt()}°F"
    else -> "${tempK.roundToInt()} K" // Kelvin is the default
}



@Preview(showBackground = true)
@Composable
fun PreviewWeatherScreen() {
    WeatherScreen()
}
