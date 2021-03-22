package com.example.androiddevchallenge.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.*
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
import com.example.androiddevchallenge.R
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

    val bottomSheetPickHeight = 280.dp

    val data by viewModel.homeDataEvent.observeAsState(arrayListOf(WeatherInfo.getWithRandomValues()))
    val selectedUnit = viewModel.selectedUnitEvent.observeAsState(TemperatureUnit.C)
    val selectedDay by viewModel.selectedWeatherDayEvent.observeAsState(WeatherInfo.getWithRandomValues())
    val sceneState = viewModel.selectedAppSceneEvent.observeAsState(AppScene.Temperature)

    val verticalGradientColor = Brush.verticalGradient(
        listOf(
            colorResource(id = R.color.blue_top),
            colorResource(id = R.color.blue_bottom)
        )
    )

    val bottomSheetScaffoldState = rememberBottomSheetScaffoldState(bottomSheetState = BottomSheetState(BottomSheetValue.Collapsed))
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
                        bottomSheetScaffoldState.bottomSheetState.expand()
                    }
                }
            )
        },
        sheetPeekHeight = bottomSheetPickHeight,
        sheetElevation = 24.dp,
        scaffoldState = bottomSheetScaffoldState,
        sheetShape = MaterialTheme.shapes.large.copy(bottomStart = CornerSize(0.dp), bottomEnd = CornerSize(0.dp)),
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