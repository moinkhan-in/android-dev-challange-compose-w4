package com.example.androiddevchallenge.ui.components

import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowRightAlt
import androidx.compose.material.icons.outlined.Opacity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.animatedVectorResource
import androidx.compose.ui.res.stringResource
import com.example.androiddevchallenge.R
import com.example.androiddevchallenge.data.WeatherInfo
import com.example.androiddevchallenge.data.WeatherType
import com.example.androiddevchallenge.data.Wind


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun WeatherAnimatedIcon(
    modifier: Modifier = Modifier,
    data: WeatherType
) {
    val image = animatedVectorResource(id = data.getIconRes())
    val atEnd by remember { mutableStateOf(false) }
    Icon(
        modifier = modifier,
        painter = image.painterFor(atEnd = atEnd),
        contentDescription = stringResource(id = data.getLabelResId()) // decorative element
    )
}

@Composable
fun WindIcon(
    modifier: Modifier = Modifier,
    data: Wind
) {
    Icon(
        Icons.Outlined.ArrowRightAlt,
        contentDescription = "%s Degree".format(data.deg.toString()),
        modifier = modifier.rotate(data.deg.toFloat())
    )
}

@Composable
fun PrecipitationIcon(
    modifier: Modifier = Modifier,
    data: WeatherInfo
) {
    Icon(
        Icons.Outlined.Opacity,
        contentDescription = stringResource(
            R.string.accessibility_rain_chances,
            data.precipitation.toString()
        ),
        modifier = modifier
    )
}
