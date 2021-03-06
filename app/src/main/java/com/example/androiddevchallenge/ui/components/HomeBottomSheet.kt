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

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.contentColorFor
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ChevronRight
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.androiddevchallenge.AppScene
import com.example.androiddevchallenge.R
import com.example.androiddevchallenge.data.TemperatureUnit
import com.example.androiddevchallenge.data.WeatherInfo

@Composable

fun HomeBottomSheet(
    modifier: Modifier = Modifier,
    data: List<WeatherInfo>,
    onItemClick: (WeatherInfo) -> Unit,
    onTabSelected: (AppScene) -> Unit,
    selectedItem: WeatherInfo,
    selectedUnit: State<TemperatureUnit>,
    toggleFullDayData: () -> Unit,
    sceneState: State<AppScene>,
    isBottomSheetExpanded: Boolean = false,
    buttonColor: Color,
) {

    Surface(
        modifier = modifier.padding(bottom = 8.dp),
        color = MaterialTheme.colors.surface,
        contentColor = contentColorFor(backgroundColor = MaterialTheme.colors.surface)
    ) {

        val rotate by animateFloatAsState(if (isBottomSheetExpanded) 90f else 0f)

        Column {

            AppTabBar(onTabItemSelected = onTabSelected, buttonColor = buttonColor)

            WeatherTilesList(
                data = data,
                onTileClick = onItemClick,
                selectedUnit = selectedUnit,
                selectedItem = selectedItem,
                sceneState = sceneState
            )

            Column(
                modifier = Modifier
                    .padding(start = 16.dp)
                    .clickable { toggleFullDayData.invoke() }
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                val resString = if (isBottomSheetExpanded) R.string.view_hourly else R.string.hide_hourly
                Text(text = stringResource(id = resString), style = MaterialTheme.typography.body2.copy(fontSize = 12.sp, fontWeight = FontWeight.SemiBold))
                Icon(Icons.Outlined.ChevronRight, null, Modifier.rotate(rotate))
            }

            val allChild = remember { data.flatMap { it.isParentItem = true; it.allDayData }.toMutableList() }
            WeatherTilesListWithHeader(
                data = allChild,
                selectedUnit = selectedUnit,
                selectedItem = selectedItem,
                sceneState = sceneState,
            )
        }
    }
}
