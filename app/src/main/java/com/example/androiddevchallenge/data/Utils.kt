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
package com.example.androiddevchallenge.data

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

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
                ),
                isParentItem = false,
                allDayData = arrayListOf()
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
    object H_A : SimpleDateFormatString()
    object EEE_DD_mmm : SimpleDateFormatString()
    object DD_MMM : SimpleDateFormatString()

    fun getFormat(): String {
        return when (this) {
            EEE -> "EEE"
            EEEE -> "EEEE"
            EEEEE -> "EEEEE"
            H_A -> "h a"
            EEE_DD_mmm -> "EEE dd MMM"
            DD_MMM -> "dd MMM"
        }
    }
}
