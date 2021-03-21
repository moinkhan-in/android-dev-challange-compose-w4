package com.example.androiddevchallenge.ui.components

import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowForward
import androidx.compose.material.icons.outlined.ArrowRight
import androidx.compose.material.icons.outlined.ArrowRightAlt
import androidx.compose.material.icons.outlined.Navigation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.animatedVectorResource
import androidx.compose.ui.res.stringResource
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
