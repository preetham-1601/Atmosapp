package com.example.atmosapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.atmosapp.models.WeatherResponse
import com.example.atmosapp.repository.WeatherRepository
import kotlinx.coroutines.launch

class WeatherViewModel(private val repository: WeatherRepository) : ViewModel() {

    private val apiKey = "63a996eb2c67b6f4a973987a3a77cbfd"

    private val _weatherData = MutableLiveData<WeatherResponse>()
    val weatherData: LiveData<WeatherResponse> = _weatherData

    fun loadWeather(city: String) {
        viewModelScope.launch {
            try {
                val weatherResponse = repository.getCurrentWeather(city, apiKey)
                _weatherData.postValue(weatherResponse)
            } catch (e: Exception) {
                // Handle the error appropriately
            }
        }
    }
}