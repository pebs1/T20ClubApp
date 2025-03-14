package fm.mrc.t20clubapp.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import fm.mrc.t20clubapp.data.KiteFlyingCalculator
import fm.mrc.t20clubapp.data.KiteFlyingScore
import fm.mrc.t20clubapp.data.WeatherApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class WeatherViewModel : ViewModel() {
    private val _uiState = MutableStateFlow<WeatherUiState>(WeatherUiState.Loading)
    val uiState: StateFlow<WeatherUiState> = _uiState.asStateFlow()

    init {
        fetchWeatherData()
    }

    private fun fetchWeatherData() {
        viewModelScope.launch {
            try {
                val response = WeatherApi.getWeatherForecast()
                val scores = response.list
                    .distinctBy { KiteFlyingCalculator.formatDate(it.dt) } // Ensure one forecast per day
                    .take(7)  // Take only the first 7 days
                    .map { forecast ->
                        val windSpeedMph = KiteFlyingCalculator.convertMpsToMph(forecast.wind.speed)
                        KiteFlyingScore(
                            date = KiteFlyingCalculator.formatDate(forecast.dt),
                            score = KiteFlyingCalculator.calculateScore(windSpeedMph),
                            windSpeed = windSpeedMph,
                            rainChance = forecast.pop,
                            weatherDescription = forecast.weather.firstOrNull()?.description?.capitalize() ?: ""
                        )
                    }
                _uiState.value = WeatherUiState.Success(scores)
            } catch (e: Exception) {
                _uiState.value = WeatherUiState.Error(e.message ?: "Unknown error occurred")
            }
        }
    }

    fun refresh() {
        fetchWeatherData()
    }
}

sealed class WeatherUiState {
    object Loading : WeatherUiState()
    data class Success(val scores: List<KiteFlyingScore>) : WeatherUiState()
    data class Error(val message: String) : WeatherUiState()
}

private fun String.capitalize(): String {
    return this.split(" ").joinToString(" ") { word ->
        word.replaceFirstChar { char -> char.uppercase() }
    }
} 