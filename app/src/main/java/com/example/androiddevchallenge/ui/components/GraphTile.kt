package com.example.androiddevchallenge.ui.components

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowRightAlt
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.unit.dp
import com.example.androiddevchallenge.AppScene
import com.example.androiddevchallenge.data.SimpleDateFormatString
import com.example.androiddevchallenge.data.TemperatureUnit
import com.example.androiddevchallenge.data.WeatherInfo
import com.example.androiddevchallenge.data.getDayFormat

@Composable
fun GraphTile(
    item: WeatherInfo,
    unit: State<TemperatureUnit>,
    state: State<AppScene>,
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {

        Crossfade(targetState = state.value) {
            when (it) {
                is AppScene.Temperature -> TemperatureTile(unit = unit, item = item)
                is AppScene.Wind -> WindTile(item = item)
            }
        }

        Text(text = item.getDayFormat(SimpleDateFormatString.H_A), style = MaterialTheme.typography.body2)
    }
}

@Composable
fun WindTile(
    modifier: Modifier = Modifier,
    item: WeatherInfo
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = item.displaySpeed(),
            style = MaterialTheme.typography.body1
        )

        Icon(
            Icons.Filled.ArrowRightAlt,
            contentDescription = null,
            modifier = Modifier.rotate(item.windDirection.deg.toFloat())
        )
    }
}

@Composable
fun TemperatureTile(
    modifier: Modifier = Modifier,
    unit: State<TemperatureUnit>,
    item: WeatherInfo
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        TemperatureText(unit = unit, value = item.temp, style = MaterialTheme.typography.body1)
    }
}

@Composable
fun GraphTileList(
    modifier: Modifier = Modifier,
    items: List<WeatherInfo>,
    unit: State<TemperatureUnit>,
    state: State<AppScene>,
) {

    val listState = rememberLazyListState()
    LazyRow(
        modifier = modifier,
        state = listState,
        contentPadding = PaddingValues(16.dp),
    ) {
        items(items) {
            GraphTile(item = it, unit = unit, state = state)
        }
    }
}