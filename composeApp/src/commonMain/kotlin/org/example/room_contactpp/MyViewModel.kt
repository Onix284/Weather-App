package org.example.room_contactpp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.example.room_contactpp.Models.WeatherApiResponse

class MyViewModel : ViewModel() {

    private val _state = MutableStateFlow(
        AppState(
        isLoading = false,
        WeatherResponse = null,
        error = null,
        ideal = true
        )
    )

    val state = _state.asStateFlow()

    fun getWeather(city : String){
        viewModelScope.launch {
            _state.update {
                it.copy(isLoading = true, ideal = false)
            }
            try {
                val weather = KtorClient.GetWeatherByCity(city)
                _state.update {
                    it.copy(isLoading = false, error = null, WeatherResponse = weather)
                }
            }
            catch (ex : Exception){
                _state.update {
                    it.copy(isLoading = false, error = ex.message)
                }
            }
        }
    }
}

data class AppState(
    val isLoading: Boolean = false,
    val WeatherResponse : WeatherApiResponse? = null,
    val error : String? = null,
    val ideal : Boolean = true
)