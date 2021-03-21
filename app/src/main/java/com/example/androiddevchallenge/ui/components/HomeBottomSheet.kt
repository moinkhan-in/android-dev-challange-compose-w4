package com.example.androiddevchallenge.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import com.example.androiddevchallenge.AppScene
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
    sceneState: State<AppScene>
) {

    Surface(
        modifier = modifier,
        color = MaterialTheme.colors.surface,
        contentColor = contentColorFor(backgroundColor = MaterialTheme.colors.surface)
    ) {

        Column {
            AppTabBar(onTabItemSelected = onTabSelected)
            WeatherTilesList(
                data = data,
                onTileClick = onItemClick,
                selectedUnit = selectedUnit,
                selectedItem = selectedItem,
                sceneState = sceneState
            )
        }
    }
}