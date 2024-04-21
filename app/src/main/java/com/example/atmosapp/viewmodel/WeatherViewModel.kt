package com.example.atmosapp.viewmodel


import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.atmosapp.models.WeatherResponse
import com.example.atmosapp.repository.WeatherRepository
import kotlinx.coroutines.launch

class WeatherViewModel(private val repository: WeatherRepository) : ViewModel() {

    var weatherData = mutableStateOf<WeatherResponse?>(null)
    var isLoading = mutableStateOf(false)
    var errorMessage = mutableStateOf("")

    private val apiKey = "63a996eb2c67b6f4a973987a3a77cbfd"
    fun loadWeather(city: String) {
        viewModelScope.launch {
            isLoading.value = true
            errorMessage.value = ""
            try {
                val response = repository.getCurrentWeather(city, apiKey)
                weatherData.value = response
                errorMessage.value = ""
            } catch (e: Exception) {
                errorMessage.value = "Data You entered is Incorrect."
            } finally {
                isLoading.value = false
            }
        }
    }
}