package com.example.atmosapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.atmosapp.repository.WeatherRepository
import kotlinx.coroutines.launch

class WeatherViewModel(private val repository: WeatherRepository) : ViewModel() {

    private val apiKey = "63a996eb2c67b6f4a973987a3a77cbfd"
    fun loadWeather(city: String, apiKey: String) {
        viewModelScope.launch {
            val weatherData = repository.getCurrentWeather(city, apiKey)
            // Update LiveData/State with weatherData
        }
    }
}