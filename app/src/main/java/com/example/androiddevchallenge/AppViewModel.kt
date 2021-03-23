/*
 * Copyright 2021 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.androiddevchallenge

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androiddevchallenge.data.TemperatureUnit
import com.example.androiddevchallenge.data.WeatherInfo
import com.example.androiddevchallenge.data.WeatherProvider
import com.example.androiddevchallenge.data.WeatherProviderImpl
import kotlinx.coroutines.launch

class AppViewModel : ViewModel() {

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
    object Temperature : AppScene()
    object Precipitation : AppScene()
    object Wind : AppScene()

    fun toDisplay(): Int {
        return when (this) {
            Precipitation -> R.string.precipitation
            Temperature -> R.string.temperature
            Wind -> R.string.wind
        }
    }
}
