package com.example.atmosapp.repository

import com.example.atmosapp.models.WeatherResponse
import com.example.atmosapp.network.WeatherApiService

class WeatherRepository(private val apiService: WeatherApiService) {
    suspend fun getCurrentWeather(city: String, apiKey: String): WeatherResponse {
        return apiService.getCurrentWeather(city, apiKey)
    }
}