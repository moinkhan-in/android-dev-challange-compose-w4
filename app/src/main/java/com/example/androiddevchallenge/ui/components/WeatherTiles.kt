package com.example.androiddevchallenge.ui.components

import androidx.compose.animation.Crossfade
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.androiddevchallenge.AppScene
import com.example.androiddevchallenge.AppViewModel
import com.example.androiddevchallenge.R
import com.example.androiddevchallenge.data.*
import kotlinx.coroutines.launch
import java.util.*


@Composable
fun WeatherTile(
    modifier: Modifier = Modifier,
    item: WeatherInfo,
    selectedUnit: State<TemperatureUnit>,
    isSelected: Boolean = false,
    sceneState: State<AppScene>,
    titleDateFormat: SimpleDateFormatString = SimpleDateFormatString.EEE,
    wrapSelectedItemInCard: Boolean = true
) {

    val alpha by animateFloatAsState(if (isSelected) 1f else 0.5f)
    val elevation by animateDpAsState(if (isSelected && wrapSelectedItemInCard) (4).dp else 0.dp)

    Card(elevation = elevation, modifier = Modifier.padding(4.dp)) {

        Column(
            modifier = modifier
                .width(80.dp)
                .padding(vertical = 16.dp)
                .alpha(alpha),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {

            Text(
                text = item.getDayFormat(titleDateFormat),
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
                    is AppScene.Temperature -> WeatherSmallIcon(data = item.type)
                    is AppScene.Wind -> WindIcon(
                        data = item.windDirection,
                        resDrawable = R.drawable.baseline_arrow_right_alt_black_18dp
                    )
                    is AppScene.Precipitation -> PrecipitationIcon(
                        data = item,
                        resDrawable = R.drawable.baseline_opacity_black_18dp
                    )
                }
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
    sceneState: State<AppScene>,
) {

    LazyRow(
        modifier = modifier,
        contentPadding = PaddingValues(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        items(data) {
            WeatherTile(
                modifier = Modifier.clickable { onTileClick(it) },
                item = it,
                selectedUnit = selectedUnit,
                isSelected = it == selectedItem,
                sceneState = sceneState
            )
        }
    }
}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun WeatherTilesListWithHeader(
    modifier: Modifier = Modifier,
    data: List<WeatherInfo>,
    selectedUnit: State<TemperatureUnit>,
    selectedItem: WeatherInfo?,
    sceneState: State<AppScene>,
) {

    val listState = rememberLazyListState()
    val scope = rememberCoroutineScope()
    scope.launch {
        listState.animateScrollToItem(data.indexOfFirst {
            it.timeStamp == (selectedItem?.timeStamp ?: 0L)
        })
    }

    LazyRow(
        modifier = modifier,
        state = listState,
        contentPadding = PaddingValues(horizontal = 8.dp, vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        data.forEach { dataItem ->
            if (dataItem.isBeginning()) {
                stickyHeader { WeatherHeaderTile(item = dataItem) }
            } else {
                item {
                    WeatherTile(
                        item = dataItem,
                        selectedUnit = selectedUnit,
                        isSelected = true,
                        sceneState = sceneState,
                        titleDateFormat = SimpleDateFormatString.H_A,
                        wrapSelectedItemInCard = false
                    )
                }
            }
        }
    }
}

@Composable
fun WeatherHeaderTile(
    item: WeatherInfo?
) {
    val weekDat =
        item?.getDayFormat(SimpleDateFormatString.EEE)?.split("")?.joinToString("\n")?.toUpperCase()
    Card(elevation = 4.dp, modifier = Modifier.padding(start = 16.dp)) {
        Text(
            text = weekDat.toString(),
            style = MaterialTheme.typography.h2.copy(fontWeight = FontWeight.Bold),
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
        )
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