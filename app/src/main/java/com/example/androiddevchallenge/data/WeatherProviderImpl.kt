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

import java.util.Calendar
import java.util.Date

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
