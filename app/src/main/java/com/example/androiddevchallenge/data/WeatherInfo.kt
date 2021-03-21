package com.example.androiddevchallenge.data

import com.example.androiddevchallenge.R
import java.util.*
import kotlin.random.Random

data class WeatherInfo(
    val temp: Int,
    val min: Int,
    val max: Int,
    val windDirection: Wind,
    val timeStamp: Long = 0L,
    val type: WeatherType = WeatherType.Clear,
    val location: Location = Location("Bardoli", "Gujarat"),
    val allDayData: ArrayList<WeatherInfo> = arrayListOf(),
    val precipitation: Int = Random.nextInt(1, 25)
) {

    companion object {
        fun getWithRandomValues(timeStamp: Long = Date().time): WeatherInfo {
            val temperature = Random.nextInt(-10, 50)
            val maxTemperature = Random.nextInt(-10, temperature + 1)
            val minTemperature = Random.nextInt(-10, maxTemperature + 1)

            return WeatherInfo(
                temp = temperature,
                min = minTemperature,
                max = maxTemperature,
                windDirection = Wind.getWithRandomValues(),
                timeStamp = timeStamp
            )
        }
    }

    fun displaySpeed(): String {
        return "%s km/h".format(windDirection.speed.toString())
    }

    fun displayPrecipitation(): String {
        return "$precipitation%"
    }
}

data class Location(
    val city: String,
    val state: String
)

data class Wind(
    val speed: Int,
    val deg: Int,
) {
    companion object {

        fun getWithRandomValues(): Wind {
            return Wind(
                speed = Random.nextInt(0, 30),
                deg = Random.nextInt(0, 360)
            )
        }
    }
}

sealed class TemperatureUnit {
    object C : TemperatureUnit()
    object F : TemperatureUnit()

    fun toConversion(value: Int): Int {
        return when (this) {
            C -> value
            F -> (value / 5 * 9) + 32
        }
    }

    fun getLabel(): Int {
        return when (this) {
            C -> R.string.celsius_unit
            F -> R.string.fahrenheit_unit
        }
    }
}

sealed class WeatherType {
    object Clear : WeatherType()
    object Cloudy : WeatherType()
    object Rainy : WeatherType()

    fun getLabelResId(): Int {
        return when (this) {
            Clear -> R.string.w_clear
            Cloudy -> R.string.w_cloudy
            Rainy -> R.string.w_rainy
        }
    }

    fun getIconRes(): Int {
        return when (this) {
            Cloudy -> R.drawable.clear_day
            else -> R.drawable.clear_day
        }
    }
}