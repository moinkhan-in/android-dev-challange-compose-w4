package com.example.androiddevchallenge.ui.components

import androidx.compose.animation.Crossfade
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.text.TextStyle
import com.example.androiddevchallenge.data.TemperatureUnit
import com.example.androiddevchallenge.data.toTemperature


@Composable
fun TemperatureText(
    unit: State<TemperatureUnit>,
    value: Int,
    style: TextStyle,
) {

    Crossfade(
        targetState = unit.value,
    ) {
        Text(
            text = value.toTemperature(it),
            style = style,
        )
    }
}