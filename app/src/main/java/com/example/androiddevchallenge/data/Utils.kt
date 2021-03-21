package com.example.androiddevchallenge.data

import java.text.SimpleDateFormat
import java.util.*

fun WeatherInfo.fillTodayData(second: WeatherInfo) {
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

fun Int.toTemperature(toUnit: TemperatureUnit = TemperatureUnit.C): String {
    val actualValue = toUnit.toConversion(this).toString().format("%1$4s")
    return "$actualValue°"
}


fun WeatherInfo.getDayFormat(format: SimpleDateFormatString = SimpleDateFormatString.EEE): String {
    return SimpleDateFormat(format.getFormat(), Locale.getDefault()).format(Date(timeStamp))
}

sealed class SimpleDateFormatString {
    object EEE : SimpleDateFormatString()
    object EEEE : SimpleDateFormatString()
    object EEEEE : SimpleDateFormatString()
    object H_A: SimpleDateFormatString()
    object EEE_DD_mmm: SimpleDateFormatString()

    fun getFormat(): String {
        return when (this) {
            EEE -> "EEE"
            EEEE -> "EEEE"
            EEEEE -> "EEEEE"
            H_A -> "h a"
            EEE_DD_mmm -> "EEE dd MMM"
        }
    }
}
