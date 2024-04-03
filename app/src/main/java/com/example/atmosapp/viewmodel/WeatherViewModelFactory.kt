package com.example.atmosapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.atmosapp.network.RetrofitClient
import com.example.atmosapp.repository.WeatherRepository

class WeatherViewModelFactory : ViewModelProvider.Factory {
    private val repository = WeatherRepository(RetrofitClient.apiService)

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WeatherViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return WeatherViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}