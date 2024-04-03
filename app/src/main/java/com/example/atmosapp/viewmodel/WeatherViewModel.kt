package com.example.atmosapp.viewmodel

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.atmosapp.models.WeatherResponse
import com.example.atmosapp.repository.WeatherRepository
import kotlinx.coroutines.launch

class WeatherViewModel(private val repository: WeatherRepository) : ViewModel() {

    var weatherData = mutableStateOf<WeatherResponse?>(null)

    private val apiKey = "63a996eb2c67b6f4a973987a3a77cbfd"
    fun loadWeather(city: String) {
        viewModelScope.launch {
            try {
                val response = repository.getCurrentWeather(city, apiKey)
                weatherData.value = response// Handle data (update LiveData or state)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}