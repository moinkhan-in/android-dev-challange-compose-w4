package com.example.androiddevchallenge.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.androiddevchallenge.AppScene
import com.example.androiddevchallenge.data.SimpleDateFormatString
import com.example.androiddevchallenge.data.TemperatureUnit
import com.example.androiddevchallenge.data.WeatherInfo
import com.example.androiddevchallenge.data.getDayFormat

@Composable

fun HomeBottomSheet(
    modifier: Modifier = Modifier,
    data: List<WeatherInfo>,
    onItemClick: (WeatherInfo) -> Unit,
    onTabSelected: (AppScene) -> Unit,
    selectedItem: WeatherInfo,
    selectedUnit: State<TemperatureUnit>,
    toggleFullDayData: () -> Unit,
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

            Button(
                onClick = toggleFullDayData,
                modifier = Modifier.padding(start = 16.dp)
            ) {
                Text(text = "More data for: " + selectedItem.getDayFormat(SimpleDateFormatString.DD_MMM))
            }

            val allData = data.flatMap { it.allDayData }.toMutableList()
            WeatherTilesList(
                data = allData,
                onTileClick = {},
                selectedUnit = selectedUnit,
                selectedItem = selectedItem,
                sceneState = sceneState,
                tileFor = TileFor.HOUR
            )
        }
    }
}