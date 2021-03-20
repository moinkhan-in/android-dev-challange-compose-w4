package com.example.androiddevchallenge.ui.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.androiddevchallenge.AppViewModel
import com.example.androiddevchallenge.Scene
import com.example.androiddevchallenge.data.TemperatureUnit
import com.example.androiddevchallenge.ui.components.GraphTileList
import com.example.androiddevchallenge.ui.components.HomeTile
import com.example.androiddevchallenge.ui.components.TabBar
import com.example.androiddevchallenge.ui.components.WeatherTilesList

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: AppViewModel
) {

    val data by viewModel.homeDataEvent.observeAsState()
    val selectedUnit = viewModel.selectedUnitEvent.observeAsState(TemperatureUnit.C)
    val selectedDay by viewModel.selectedWeatherDayEvent.observeAsState()
    val sceneState = viewModel.selectedSceneEvent.observeAsState(Scene.Temperature)

    ConstraintLayout(
        modifier.fillMaxSize()
    ) {

        val (weatherList, mainTile, graph, tabBar) = createRefs()

        selectedDay?.let { item ->
            HomeTile(
                modifier = Modifier.constrainAs(mainTile) {
                    top.linkTo(parent.top)
                },
                data = item,
                selectedUnit = selectedUnit,
                onUnitChanged = { viewModel.updateSelectedUnit(it) }
            )
        }

        TabBar(
            modifier = Modifier.constrainAs(tabBar) {
                bottom.linkTo(graph.top)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            },
            onTabItemSelected = { viewModel.selectedSceneEvent.value = it }
        )

        val allData = data.orEmpty().flatMap { it.allDayData }.toMutableList()
        GraphTileList(
            modifier = Modifier.constrainAs(graph) {
                bottom.linkTo(weatherList.top)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            },
            items = allData,
            unit = selectedUnit,
            state = sceneState
        )

        WeatherTilesList(
            modifier = Modifier.constrainAs(weatherList) {
                bottom.linkTo(parent.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            },
            data = data.orEmpty(),
            onTileClick = { viewModel.updateSelectedWeatherDay(it) },
            selectedUnit = selectedUnit,
            selectedItem = selectedDay
        )
    }
}

@Composable
@Preview
private fun HomeScreenPreview() {
    HomeScreen(viewModel = AppViewModel())
}