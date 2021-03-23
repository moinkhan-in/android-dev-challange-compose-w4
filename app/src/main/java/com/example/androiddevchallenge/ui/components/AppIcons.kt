package com.example.androiddevchallenge.ui.components

import androidx.compose.animation.fadeIn
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowRightAlt
import androidx.compose.material.icons.outlined.Opacity
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.androiddevchallenge.R
import com.example.androiddevchallenge.data.WeatherInfo
import com.example.androiddevchallenge.data.WeatherType
import com.example.androiddevchallenge.data.Wind
import com.google.accompanist.coil.CoilImage

@Composable
fun WeatherSmallIcon(
    modifier: Modifier = Modifier,
    data: WeatherType
) {
    CoilImage(
        fadeIn = true,
        modifier = modifier.size(28.dp),
        data = data.getIconRes(),
        contentDescription = stringResource(id = data.getLabelResId())
    )
}

@Composable
fun WeatherLargeIcon(
    modifier: Modifier = Modifier,
    data: WeatherType
) {
    CoilImage(
        fadeIn = true,
        modifier = modifier.size(120.dp),
        data = data.getImageRes(),
        contentDescription = stringResource(id = data.getLabelResId())
    )
}

@Composable
fun WindIcon(
    modifier: Modifier = Modifier,
    data: Wind,
    resDrawable: Int
) {

    CoilImage(
        fadeIn = true,
        data = resDrawable,
        contentDescription = "%s Degree".format(data.deg.toString()),
        modifier = modifier.size(28.dp).rotate(data.deg.toFloat())
    )
}

@Composable
fun PrecipitationIcon(
    modifier: Modifier = Modifier,
    data: WeatherInfo,
    resDrawable: Int
) {
    CoilImage(
        fadeIn = true,
        data = resDrawable,
        contentDescription = stringResource(R.string.accessibility_rain_chances, data.precipitation.toString()),
        modifier = modifier.size(28.dp)
    )
}
