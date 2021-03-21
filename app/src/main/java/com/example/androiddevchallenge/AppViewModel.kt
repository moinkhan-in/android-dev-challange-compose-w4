package com.example.androiddevchallenge

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androiddevchallenge.data.TemperatureUnit
import com.example.androiddevchallenge.data.WeatherInfo
import com.example.androiddevchallenge.data.WeatherProvider
import com.example.androiddevchallenge.data.WeatherProviderImpl
import kotlinx.coroutines.launch

class AppViewModel: ViewModel() {

    private val weatherProvider: WeatherProvider = WeatherProviderImpl()

    val homeDataEvent: MutableLiveData<List<WeatherInfo>> = MutableLiveData()
    val selectedUnitEvent: MutableLiveData<TemperatureUnit> = MutableLiveData()
    val selectedWeatherDayEvent: MutableLiveData<WeatherInfo> = MutableLiveData()
    val selectedAppSceneEvent: MutableLiveData<AppScene> = MutableLiveData()

    fun fetchDataForHome() {
        viewModelScope.launch {
            homeDataEvent.value = weatherProvider.getByCityName("Bardoli", 8)
            selectedWeatherDayEvent.value = homeDataEvent.value?.firstOrNull()
        }
    }

    fun updateSelectedUnit(newUnit: TemperatureUnit) {
        selectedUnitEvent.value = newUnit
    }

    fun updateSelectedWeatherDay(newInfo: WeatherInfo) {
        selectedWeatherDayEvent.value = newInfo
    }

    fun updateScene(newAppScene: AppScene) {
        selectedAppSceneEvent.value = newAppScene
    }
}

sealed class AppScene {
    object Temperature: AppScene()
    object Precipitation: AppScene()
    object Wind: AppScene()

    fun toDisplay(): Int {
        return when (this) {
            Precipitation -> R.string.precipitation
            Temperature -> R.string.temperature
            Wind -> R.string.wind
        }
    }
}