package com.example.androiddevchallenge.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.androiddevchallenge.AppScene
import com.example.androiddevchallenge.AppViewModel
import com.example.androiddevchallenge.R
import com.example.androiddevchallenge.data.TemperatureUnit
import com.example.androiddevchallenge.data.WeatherInfo
import com.example.androiddevchallenge.ui.components.HomeBottomSheet
import com.example.androiddevchallenge.ui.components.HomeTile

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: AppViewModel
) {

    val data by viewModel.homeDataEvent.observeAsState(arrayListOf(WeatherInfo.getWithRandomValues()))
    val selectedUnit = viewModel.selectedUnitEvent.observeAsState(TemperatureUnit.C)
    val selectedDay by viewModel.selectedWeatherDayEvent.observeAsState(WeatherInfo.getWithRandomValues())
    val sceneState = viewModel.selectedAppSceneEvent.observeAsState(AppScene.Temperature)

    val verticalGradientColor = Brush.verticalGradient(listOf(colorResource(id = R.color.blue_top), colorResource(id = R.color.blue_bottom)))
    ConstraintLayout(modifier.fillMaxSize()) {
        Column {
            HomeTile(
                modifier = Modifier
                    .weight(2.5f)
                    .background(verticalGradientColor)
                    .fillMaxWidth(),
                data = selectedDay,
                selectedUnit = selectedUnit,
                onUnitChanged = { viewModel.updateSelectedUnit(it) }
            )

            HomeBottomSheet(
                modifier = Modifier.weight(1f),
                data = data,
                onItemClick = { viewModel.selectedWeatherDayEvent.value = it },
                onTabSelected = { viewModel.selectedAppSceneEvent.value = it },
                selectedUnit = selectedUnit,
                selectedItem = selectedDay,
                sceneState = sceneState,
            )
        }
    }
}

@Composable
@Preview
private fun HomeScreenPreview() {
    HomeScreen(viewModel = AppViewModel())
}