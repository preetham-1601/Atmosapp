package com.example.atmosapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.atmosapp.i.WeatherApp
import com.example.atmosapp.i.WeatherScreen
import com.example.atmosapp.viewmodel.WeatherViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                // Initialize ViewModel
                val weatherViewModel: WeatherViewModel = viewModel()
                WeatherScreen(weatherViewModel)
            }
        }
    }
}
