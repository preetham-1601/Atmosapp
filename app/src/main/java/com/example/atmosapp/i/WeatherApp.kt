package com.example.atmosapp.i

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.material3.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.atmosapp.viewmodel.WeatherViewModel
import com.example.atmosapp.viewmodel.WeatherViewModelFactory

// Assuming your data classes are defined as shown before
@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun WeatherScreen() {
    val viewModel: WeatherViewModel = viewModel(factory = WeatherViewModelFactory())
    val weather = viewModel.weatherData.value
    var searchText by remember { mutableStateOf("") }
    var active by remember { mutableStateOf(false) }
    val keyboardController = LocalSoftwareKeyboardController.current

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    if (!active) {
                        Text("Weather App")
                    } else {
                        TextField(
                            value = searchText,
                            onValueChange = { searchText = it },
                            placeholder = { Text("Search City") },
                            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                            keyboardActions = KeyboardActions(onSearch = {
                                viewModel.loadWeather(searchText)
                                active = false
                                keyboardController?.hide()
                            })
                        )
                    }
                },
                actions = {
                    if (!active) {
                        IconButton(onClick = { active = true }) {
                            Icon(Icons.Default.Search, contentDescription = "Search")
                        }
                    } else {
                        IconButton(onClick = { active = false; searchText = "" }) {
                            Icon(Icons.Default.Close, contentDescription = "Close")
                        }
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(modifier = Modifier
            .padding(innerPadding)
            .padding(16.dp)
            .verticalScroll(rememberScrollState())) {
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
}

@Preview(showBackground = true)
@Composable
fun PreviewWeatherApp() {
    WeatherScreen()
}
