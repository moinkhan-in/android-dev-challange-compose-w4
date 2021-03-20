package com.example.androiddevchallenge.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animate
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.androiddevchallenge.AppViewModel
import com.example.androiddevchallenge.data.TemperatureUnit
import com.example.androiddevchallenge.data.WeatherInfo
import com.example.androiddevchallenge.data.Wind
import com.example.androiddevchallenge.data.toTemperature
import java.util.*

@Composable
fun WeatherTile(
    modifier: Modifier = Modifier,
    item: WeatherInfo,
    selectedUnit: State<TemperatureUnit>,
    isSelected: Boolean = false,
) {

    val scale by animateFloatAsState(if (isSelected) 1.1f else 1f)
    val border by animateDpAsState(if (isSelected) 1.dp else 0.dp)
    val borderColor by animateColorAsState(if (isSelected) MaterialTheme.colors.primary else Color.Transparent)

    Card(
        modifier = modifier
            .scale(scale = scale)
            .border(border, borderColor, MaterialTheme.shapes.medium),
        shape = MaterialTheme.shapes.medium,
    ) {
        Column(
            modifier = modifier.padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = item.getWeekDay(), style = MaterialTheme.typography.h2)

            Spacer(modifier = Modifier.size(4.dp))

            FloatingActionButton(
                onClick = { /*TODO*/ },
                backgroundColor = Color.Yellow,
                elevation = FloatingActionButtonDefaults.elevation(defaultElevation = 4.dp, pressedElevation = 4.dp)
            ) {

            }

            Spacer(modifier = Modifier.size(4.dp))

            Row {
                TemperatureText(unit = selectedUnit, value = item.temp, style = MaterialTheme.typography.body1)
                Spacer(modifier = Modifier.size(8.dp))
                TemperatureText(unit = selectedUnit, value = item.min, style = MaterialTheme.typography.body2)
            }
        }
    }
}

@Preview
@Composable
private fun WeatherTilePreview() {
    val fakeItem = WeatherInfo(32, 32, 32, Wind(25, 48), timeStamp = Date().time)
    WeatherTile(item = fakeItem, selectedUnit = mutableStateOf(TemperatureUnit.C))
}

@Composable
fun WeatherTilesList(
    modifier: Modifier = Modifier,
    data: List<WeatherInfo>,
    onTileClick: (WeatherInfo) -> Unit,
    selectedUnit: State<TemperatureUnit>,
    selectedItem: WeatherInfo?
) {

    val listState = rememberLazyListState()
    LazyRow(
        modifier = modifier,
        state = listState,
        contentPadding = PaddingValues(16.dp)
    ) {

        items(data) {
            WeatherTile(
                modifier = Modifier.clickable { onTileClick(it) },
                item = it,
                selectedUnit = selectedUnit,
                isSelected = it == selectedItem,
            )
            Spacer(modifier = Modifier.padding(8.dp))
        }
    }
}


@Composable
@Preview
private fun WeatherTileListPreview() {
    val data = AppViewModel().homeDataEvent.value
    WeatherTilesList(data = data.orEmpty(), onTileClick = {  }, selectedUnit = mutableStateOf(TemperatureUnit.C), selectedItem = WeatherInfo.getWithRandomValues())
}