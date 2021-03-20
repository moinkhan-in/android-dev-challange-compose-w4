package com.example.androiddevchallenge.data

import androidx.compose.ui.unit.sp
import com.example.androiddevchallenge.R
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import kotlin.random.Random

data class WeatherInfo(
    val temp: Int,
    val min: Int,
    val max: Int,
    val windDirection: Wind,
    val timeStamp: Long = 0L,
    val type: WeatherType = WeatherType.Clear,
    val allDayData: ArrayList<WeatherInfo> = arrayListOf()
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

    fun getWeekDay(format: WeekDayFormat = WeekDayFormat.EEE): String {
        return SimpleDateFormat(format.getFormat(), Locale.getDefault()).format(Date(timeStamp))
    }

    fun getDayFormat(format: String = "HH a"): String {
        return SimpleDateFormat(format, Locale.getDefault()).format(Date(timeStamp))
    }

    fun fillTodayData(second: WeatherInfo) {

        val tempFraction = (second.temp - temp) / 24f
        val minTempFraction = (second.min - min) / 24f
        val maxTempFraction = (second.max - max) / 24f
        val windSpeedFraction = (second.windDirection.speed - windDirection.speed) / 24f
        val windDegFraction = (second.windDirection.deg - windDirection.deg) / 24f


        val newTime = Calendar.getInstance()
        newTime.resetTimeToZero()
        newTime.time = Date(timeStamp)

        repeat(24) {
            newTime.set(Calendar.HOUR_OF_DAY, it)
            allDayData.add(
                copy(
                    timeStamp = newTime.time.time,
                    temp = (temp + (tempFraction * it)).toInt(),
                    min = (min + (minTempFraction * it)).toInt(),
                    max = (max + (maxTempFraction * it)).toInt(),
                    windDirection = windDirection.copy(
                        speed = (windDirection.speed + (windSpeedFraction * it)).toInt(),
                        deg = (windDirection.deg + (windDegFraction * it)).toInt()
                    )
                )
            )
        }
    }

    fun displaySpeed(): String {
        return windDirection.speed.toString().format(" km/h")
    }
}

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

fun Int.toTemperature(toUnit: TemperatureUnit = TemperatureUnit.C): String {
    val actualValue = toUnit.toConversion(this).toString().format("%1$4s")
    val isSign = toUnit != TemperatureUnit.K
    return "$actualValue${if (isSign) "°" else ""}"
}

sealed class TemperatureUnit {
    object C : TemperatureUnit()
    object F : TemperatureUnit()
    object K : TemperatureUnit()

    fun toConversion(value: Int): Int {
        return when (this) {
            C -> value
            F -> (value / 5 * 9) + 32
            K -> value + 273 + 15
        }
    }

    fun getLabel(): Int {
        return when (this) {
            C -> R.string.celsius_unit
            F -> R.string.fahrenheit_unit
            K -> R.string.celsius_unit
        }
    }
}

sealed class WeekDayFormat {
    object EEE : WeekDayFormat()
    object EEEE : WeekDayFormat()
    object EEEEE : WeekDayFormat()

    fun getFormat(): String {
        return when (this) {
            EEE -> "EEE"
            EEEE -> "EEEE"
            EEEEE -> "EEEEE"
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
}