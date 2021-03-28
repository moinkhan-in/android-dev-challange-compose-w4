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
package com.example.androiddevchallenge.ui.screens

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.BottomSheetState
import androidx.compose.material.BottomSheetValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.BottomSheetScaffold
import androidx.compose.material.MaterialTheme
import androidx.compose.material.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.androiddevchallenge.AppScene
import com.example.androiddevchallenge.AppViewModel
import com.example.androiddevchallenge.data.TemperatureUnit
import com.example.androiddevchallenge.data.WeatherInfo
import com.example.androiddevchallenge.ui.components.HomeBottomSheet
import com.example.androiddevchallenge.ui.components.HomeTile
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: AppViewModel
) {

    val bottomSheetPickHeight = 320.dp

    val data by viewModel.homeDataEvent.observeAsState(arrayListOf(WeatherInfo.getWithRandomValues()))
    val selectedUnit = viewModel.selectedUnitEvent.observeAsState(TemperatureUnit.C)
    val selectedDay by viewModel.selectedWeatherDayEvent.observeAsState(WeatherInfo.getWithRandomValues())
    val sceneState = viewModel.selectedAppSceneEvent.observeAsState(AppScene.Temperature)

    val animateTopColor = animateColorAsState(targetValue = colorResource(id = selectedDay.type.getGradientColor().first), animationSpec = tween(durationMillis = 3000, delayMillis = 0))
    val animateBottomColor = animateColorAsState(targetValue = colorResource(id = selectedDay.type.getGradientColor().second), animationSpec = tween(durationMillis = 3000, delayMillis = 1000))
    val verticalGradientColor =  Brush.verticalGradient(listOf(animateTopColor.value, animateBottomColor.value))

    val bottomSheetScaffoldState = rememberBottomSheetScaffoldState(bottomSheetState = BottomSheetState(
        BottomSheetValue.Collapsed))
    val coroutineScope = rememberCoroutineScope()
    BottomSheetScaffold(
        sheetContent = {
            HomeBottomSheet(
                data = data,
                onItemClick = { viewModel.selectedWeatherDayEvent.value = it },
                onTabSelected = { viewModel.selectedAppSceneEvent.value = it },
                selectedUnit = selectedUnit,
                selectedItem = selectedDay,
                sceneState = sceneState,
                toggleFullDayData = {
                    coroutineScope.launch {
                        if (bottomSheetScaffoldState.bottomSheetState.isCollapsed) {
                            bottomSheetScaffoldState.bottomSheetState.expand()
                        } else {
                            bottomSheetScaffoldState.bottomSheetState.collapse()
                        }
                    }
                },
                isBottomSheetExpanded = bottomSheetScaffoldState.bottomSheetState.isCollapsed,
                buttonColor = animateBottomColor.value
            )
        },
        sheetPeekHeight = bottomSheetPickHeight,
        sheetElevation = 24.dp,
        scaffoldState = bottomSheetScaffoldState,
        sheetShape = MaterialTheme.shapes.large.copy(
            bottomStart = CornerSize(0.dp),
            bottomEnd = CornerSize(0.dp)
        ),
    ) {
        HomeTile(
            modifier = Modifier
                .background(verticalGradientColor)
                .fillMaxWidth()
                .padding(bottom = bottomSheetPickHeight),
            data = selectedDay,
            selectedUnit = selectedUnit,
            onUnitChanged = { viewModel.updateSelectedUnit(it) }
        )
    }
}

@Composable
@Preview
private fun HomeScreenPreview() {
    HomeScreen(viewModel = AppViewModel())
}
