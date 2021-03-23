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
package com.example.androiddevchallenge.ui.components

import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.androiddevchallenge.R
import com.example.androiddevchallenge.data.WeatherInfo
import com.example.androiddevchallenge.data.WeatherType
import com.example.androiddevchallenge.data.Wind
import com.google.accompanist.coil.CoilImage

@Composable
fun WeatherSmallIcon(
    modifier: Modifier = Modifier,
    data: WeatherType
) {
    CoilImage(
        fadeIn = true,
        modifier = modifier.size(28.dp),
        data = data.getIconRes(),
        contentDescription = stringResource(id = data.getLabelResId())
    )
}

@Composable
fun WeatherLargeIcon(
    modifier: Modifier = Modifier,
    data: WeatherType
) {
    CoilImage(
        fadeIn = true,
        modifier = modifier.size(120.dp),
        data = data.getImageRes(),
        contentDescription = stringResource(id = data.getLabelResId())
    )
}

@Composable
fun WindIcon(
    modifier: Modifier = Modifier,
    data: Wind,
    resDrawable: Int
) {

    CoilImage(
        fadeIn = true,
        data = resDrawable,
        contentDescription = "%s Degree".format(data.deg.toString()),
        modifier = modifier.size(28.dp).rotate(data.deg.toFloat())
    )
}

@Composable
fun PrecipitationIcon(
    modifier: Modifier = Modifier,
    data: WeatherInfo,
    resDrawable: Int
) {
    CoilImage(
        fadeIn = true,
        data = resDrawable,
        contentDescription = stringResource(R.string.accessibility_rain_chances, data.precipitation.toString()),
        modifier = modifier.size(28.dp)
    )
}
