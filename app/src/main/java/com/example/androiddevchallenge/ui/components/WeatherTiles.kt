package com.example.androiddevchallenge.ui.components

import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Air
import androidx.compose.material.icons.outlined.Water
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.androiddevchallenge.AppScene
import com.example.androiddevchallenge.AppViewModel
import com.example.androiddevchallenge.R
import com.example.androiddevchallenge.data.*
import java.util.*

@Composable
fun WeatherTile(
    modifier: Modifier = Modifier,
    item: WeatherInfo,
    selectedUnit: State<TemperatureUnit>,
    isSelected: Boolean = false,
    sceneState: State<AppScene>,
) {

    val scale by animateFloatAsState(if (isSelected) 1.1f else 1f)
    val alpha by animateFloatAsState(if (isSelected) 1f else 0.5f)
    val offsetY by animateDpAsState(if (isSelected) (-8).dp else 0.dp)

    Column(
        modifier = modifier
            .padding(vertical = 8.dp, horizontal = 16.dp)
            .alpha(alpha)
            .offset(y = offsetY)
            .rotate(scale),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        Text(
            text = item.getDayFormat(SimpleDateFormatString.EEE),
            style = MaterialTheme.typography.body1
        )

        Spacer(modifier = Modifier.size(8.dp))

        Crossfade(targetState = sceneState.value) {
            when (it) {
                is AppScene.Temperature -> TemperatureText(
                    unit = selectedUnit,
                    value = item.temp,
                    style = MaterialTheme.typography.h3
                )

                is AppScene.Wind -> Text(
                    text = item.windDirection.speed.toString(),
                    style = MaterialTheme.typography.h3
                )

                is AppScene.Precipitation -> Text(
                    text = item.precipitation.toString(),
                    style = MaterialTheme.typography.h3
                )
            }
        }

        Crossfade(targetState = sceneState.value) {
            when (it) {
                is AppScene.Temperature -> TemperatureText(
                    unit = selectedUnit,
                    value = item.min,
                    style = MaterialTheme.typography.body2
                )

                is AppScene.Wind -> Text(
                    text = stringResource(R.string.km_unit),
                    style = MaterialTheme.typography.body2
                )

                is AppScene.Precipitation -> Text(
                    text = stringResource(R.string.percentage),
                    style = MaterialTheme.typography.body2
                )
            }
        }

        Spacer(modifier = Modifier.size(8.dp))

        Crossfade(targetState = sceneState.value) {
            when (it) {
                is AppScene.Temperature ->  WeatherAnimatedIcon(Modifier.size(32.dp), data = item.type)
                is AppScene.Wind -> WindIcon(data = item.windDirection)
                is AppScene.Precipitation -> Icon(Icons.Outlined.Water, null)
            }
        }
    }
}

@Preview
@Composable
private fun WeatherTilePreview() {
    val fakeItem = WeatherInfo(32, 32, 32, Wind(25, 48), timeStamp = Date().time)
    WeatherTile(
        item = fakeItem,
        selectedUnit = mutableStateOf(TemperatureUnit.C),
        sceneState = mutableStateOf(AppScene.Temperature)
    )
}

@Composable
fun WeatherTilesList(
    modifier: Modifier = Modifier,
    data: List<WeatherInfo>,
    onTileClick: (WeatherInfo) -> Unit,
    selectedUnit: State<TemperatureUnit>,
    selectedItem: WeatherInfo?,
    sceneState: State<AppScene>
) {

    val listState = rememberLazyListState()
    LazyRow(
        modifier = modifier,
        state = listState,
        contentPadding = PaddingValues(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        items(data) {
            WeatherTile(
                modifier = Modifier
                    .width(80.dp)
                    .clickable { onTileClick(it) },
                item = it,
                selectedUnit = selectedUnit,
                isSelected = it == selectedItem,
                sceneState = sceneState
            )

            Spacer(modifier = Modifier
                .padding(horizontal = 8.dp)
                .width(1.dp)
                .height(28.dp)
                .background(Color.LightGray)
            )
        }
    }
}


@Composable
@Preview
private fun WeatherTileListPreview() {
    val data = AppViewModel().homeDataEvent.value
    WeatherTilesList(
        data = data.orEmpty(),
        onTileClick = { },
        selectedUnit = mutableStateOf(TemperatureUnit.C),
        selectedItem = WeatherInfo.getWithRandomValues(),
        sceneState = mutableStateOf(AppScene.Temperature)
    )
}