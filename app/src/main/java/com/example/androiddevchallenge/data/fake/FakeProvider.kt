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
package com.example.androiddevchallenge.data.fake

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.example.androiddevchallenge.data.WeatherInfo
import com.example.androiddevchallenge.data.Wind

class FakeProvider : PreviewParameterProvider<WeatherInfo> {
    override val values = fakeItems.asSequence()
    override val count: Int = values.count()
}

val fakeItems = arrayListOf(
    WeatherInfo(32, 32, 32, Wind(25, 67)),
    WeatherInfo(32, 32, 32, Wind(25, 67)),
    WeatherInfo(32, 32, 32, Wind(25, 67)),
    WeatherInfo(32, 32, 32, Wind(25, 67)),
    WeatherInfo(32, 32, 32, Wind(25, 67)),
    WeatherInfo(32, 32, 32, Wind(25, 67))
)
