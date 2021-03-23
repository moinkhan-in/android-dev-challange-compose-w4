package com.example.androiddevchallenge.data

import java.util.*
import kotlin.random.Random

class WeatherProviderImpl : WeatherProvider {

    override fun getByCityName(name: String, futureDays: Int): List<WeatherInfo> {

        val today = Calendar.getInstance()
        today.resetTimeToZero()

        val listOfItems = arrayListOf<WeatherInfo>()
        repeat(futureDays) {
            val item = WeatherInfo.getWithRandomValues(today.time.time, index = it)
            listOfItems.add(item)
            today.add(Calendar.DAY_OF_YEAR, 1)
        }

        listOfItems.zipWithNext().forEach {
            it.first.fillTodayData(it.second)
        }

        return listOfItems
    }
}

fun Calendar.resetTimeToZero() {
    set(Calendar.HOUR_OF_DAY, 0)
    set(Calendar.MINUTE, 0)
    set(Calendar.SECOND, 0)
}

fun Calendar.isBeginning() = this.get(Calendar.HOUR_OF_DAY) == 0 && this.get(Calendar.MINUTE) == 0

fun WeatherInfo.isBeginning() =
    Calendar.getInstance().apply { time = Date(timeStamp) }.isBeginning()
