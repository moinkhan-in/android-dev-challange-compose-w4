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
    val selectedSceneEvent: MutableLiveData<Scene> = MutableLiveData()

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

    fun updateScene(newScene: Scene) {
        selectedSceneEvent.value = newScene
    }
}

sealed class Scene {
    object Temperature: Scene()
    object Precipitation: Scene()
    object Wind: Scene()

    fun toDisplay(): Int {
        return when (this) {
            Precipitation -> R.string.temperature
            Temperature -> R.string.precipitation
            Wind -> R.string.wind
        }
    }
}