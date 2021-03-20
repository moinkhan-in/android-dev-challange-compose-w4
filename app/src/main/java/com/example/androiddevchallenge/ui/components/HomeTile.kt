package com.example.androiddevchallenge.ui.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.androiddevchallenge.AppViewModel
import com.example.androiddevchallenge.R
import com.example.androiddevchallenge.data.TemperatureUnit
import com.example.androiddevchallenge.data.WeatherInfo

@Composable
fun HomeTile(
    modifier: Modifier = Modifier,
    data: WeatherInfo,
    selectedUnit: State<TemperatureUnit>,
    onUnitChanged: (TemperatureUnit) -> Unit = {},
) {
    Row(
        modifier = modifier
            .padding(16.dp)
            .fillMaxWidth(),
    ) {

        TemperatureText(
            value = data.temp,
            style = MaterialTheme.typography.h1, unit =
            selectedUnit
        )

        val celsiusAlpha by animateFloatAsState(targetValue = if (selectedUnit.value == TemperatureUnit.C) 1f else .5f)
        val fahrenheitAlpha by animateFloatAsState(targetValue = if (selectedUnit.value == TemperatureUnit.F) 1f else .5f)

        TemperatureUnitButton(TemperatureUnit.C, celsiusAlpha, onUnitChanged)
        Text(text = stringResource(R.string.unit_devider), Modifier.padding(vertical = 8.dp))
        TemperatureUnitButton(TemperatureUnit.F, fahrenheitAlpha, onUnitChanged)
    }
}


@Composable
private fun TemperatureUnitButton(
    unit: TemperatureUnit,
    alpha: Float,
    onClick: (TemperatureUnit) -> Unit,
) {
    TextButton(
        modifier = Modifier
            .alpha(alpha)
            .width(40.dp),
        onClick = { onClick.invoke(unit) },
    ) {
        Text(
            text = stringResource(unit.getLabel()),
            style = MaterialTheme.typography.h3
        )
    }
}

@Composable
@Preview
private fun HomeTilePreview() {
    val data = AppViewModel().homeDataEvent.value?.get(0)
    data?.let {
        HomeTile(data = data, selectedUnit = mutableStateOf(TemperatureUnit.C))
    }
}