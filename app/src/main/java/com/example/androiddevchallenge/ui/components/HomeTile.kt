package com.example.androiddevchallenge.ui.components

import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.androiddevchallenge.AppViewModel
import com.example.androiddevchallenge.R
import com.example.androiddevchallenge.data.SimpleDateFormatString
import com.example.androiddevchallenge.data.TemperatureUnit
import com.example.androiddevchallenge.data.WeatherInfo
import com.example.androiddevchallenge.data.getDayFormat
import java.util.*

@Composable
fun HomeTile(
    modifier: Modifier = Modifier,
    data: WeatherInfo,
    selectedUnit: State<TemperatureUnit>,
    onUnitChanged: (TemperatureUnit) -> Unit = {},
) {
    ConstraintLayout(
        modifier = modifier
            .padding(16.dp),
    ) {

        val (locationTile, centeredTile, convertUnit, waterAndAir) = createRefs()


        LocationTile(
            modifier = Modifier.constrainAs(locationTile) {
                top.linkTo(parent.top)
                start.linkTo(parent.start)
            },
            data = data
        )

        CenteredTile(
            modifier = Modifier.constrainAs(centeredTile) {
                top.linkTo(parent.top)
                start.linkTo(parent.start)
                bottom.linkTo(parent.bottom)
                end.linkTo(parent.end)
            },
            data = data,
            selectedUnit = selectedUnit
        )

        PrecipitationAndWind(
            modifier = Modifier.constrainAs(waterAndAir) {
                top.linkTo(centeredTile.bottom)
                start.linkTo(parent.start)
                bottom.linkTo(parent.bottom)
                end.linkTo(parent.end)
            },
            data = data
        )

        ConvertUnitTile(
            selectedUnit = selectedUnit,
            onUnitChanged = onUnitChanged,
            modifier = Modifier.constrainAs(convertUnit) {
                end.linkTo(parent.end)
                top.linkTo(parent.top)
            }
        )
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
private fun CenteredTile(
    modifier: Modifier = Modifier,
    data: WeatherInfo,
    selectedUnit: State<TemperatureUnit>
) {
    Column(
        modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        WeatherAnimatedIcon(data = data.type)

        TemperatureText(
            value = data.temp,
            style = MaterialTheme.typography.h1.copy(fontWeight = FontWeight.SemiBold),
            unit = selectedUnit
        )

        Text(
            text = stringResource(data.type.getLabelResId()),
            style = MaterialTheme.typography.h2
        )
    }
}

@Composable
private fun PrecipitationAndWind(
    modifier: Modifier = Modifier,
    data: WeatherInfo,
) {
    Row(modifier, Arrangement.spacedBy(16.dp)) {
        Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
            Icon(Icons.Outlined.Water, null)
            Text(text = data.precipitation.toString().format(), style = MaterialTheme.typography.h3)
        }

        Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
            WindIcon(data = data.windDirection)
            Text(text = data.displaySpeed(), style = MaterialTheme.typography.h3)
        }
    }
}

@Composable
private fun LocationTile(
    modifier: Modifier = Modifier,
    data: WeatherInfo
) {
    Row(modifier = modifier) {
        Icon(Icons.Outlined.NearMe, null)

        Column(modifier = Modifier.padding(start = 16.dp)) {
            Text(
                text = data.location.city.toUpperCase(Locale.getDefault()),
                style = MaterialTheme.typography.body1.copy(
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.2.sp
                )
            )

            Text(
                text = data.location.state,
                style = MaterialTheme.typography.body2.copy(fontWeight = FontWeight.SemiBold),
                modifier = Modifier.alpha(0.8f)
            )

            Spacer(modifier = Modifier.size(8.dp))

            Text(
                text = data.getDayFormat(SimpleDateFormatString.EEE_DD_mmm),
                style = MaterialTheme.typography.body2.copy(fontWeight = FontWeight.Bold),
            )

        }
    }
}

@Composable
private fun ConvertUnitTile(
    modifier: Modifier = Modifier,
    selectedUnit: State<TemperatureUnit>,
    onUnitChanged: (TemperatureUnit) -> Unit = {},
) {
    val celsiusAlpha by animateFloatAsState(targetValue = if (selectedUnit.value == TemperatureUnit.C) 1f else .5f)
    val fahrenheitAlpha by animateFloatAsState(targetValue = if (selectedUnit.value == TemperatureUnit.F) 1f else .5f)

    Row(modifier = modifier) {
        TemperatureUnitButton(TemperatureUnit.C, celsiusAlpha, onUnitChanged)

        Text(text = stringResource(R.string.unit_devider))

        TemperatureUnitButton(TemperatureUnit.F, fahrenheitAlpha, onUnitChanged)
    }
}

@Composable
private fun TemperatureUnitButton(
    unit: TemperatureUnit,
    alpha: Float,
    onClick: (TemperatureUnit) -> Unit,
) {
    Text(
        modifier = Modifier
            .alpha(alpha)
            .clickable { onClick.invoke(unit) }
            .width(40.dp),
        text = stringResource(unit.getLabel()),
        style = MaterialTheme.typography.h3,
        textAlign = TextAlign.Center
    )
}

@Composable
@Preview
private fun HomeTilePreview() {
    val data = AppViewModel().homeDataEvent.value?.get(0)
    data?.let {
        HomeTile(data = data, selectedUnit = mutableStateOf(TemperatureUnit.C))
    }
}