package com.example.androiddevchallenge.data

interface WeatherProvider {
    fun getByCityName(name: String, futureDays: Int = 7): List<WeatherInfo>
}