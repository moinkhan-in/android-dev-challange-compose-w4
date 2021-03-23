package com.example.androiddevchallenge.ui.screens

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.androiddevchallenge.AppScene
import com.example.androiddevchallenge.AppViewModel
import com.example.androiddevchallenge.R
import com.example.androiddevchallenge.data.TemperatureUnit
import com.example.androiddevchallenge.data.WeatherInfo
import com.example.androiddevchallenge.ui.components.HomeBottomSheet
import com.example.androiddevchallenge.ui.components.HomeTile
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: AppViewModel
) {

    val bottomSheetPickHeight = 320.dp

    val data by viewModel.homeDataEvent.observeAsState(arrayListOf(WeatherInfo.getWithRandomValues()))
    val selectedUnit = viewModel.selectedUnitEvent.observeAsState(TemperatureUnit.C)
    val selectedDay by viewModel.selectedWeatherDayEvent.observeAsState(WeatherInfo.getWithRandomValues())
    val sceneState = viewModel.selectedAppSceneEvent.observeAsState(AppScene.Temperature)

    //val selectedDayTransition = updateTransition(targetState = selectedDay)
   // val animateTopColor by selectedDayTransition.animateColor(transitionSpec = { tween(durationMillis = 3000) })  { colorResource(id = it.type.getGradientColor().first) }
  //  val animateBottomColor by  selectedDayTransition.animateColor(transitionSpec = { tween(durationMillis = 3000, delayMillis = 1000) })  { colorResource(id = it.type.getGradientColor().second) }
//
//    val verticalGradientColor = Brush.verticalGradient(listOf(animateTopColor, animateBottomColor))
    val verticalGradientColor = Brush.verticalGradient(listOf(colorResource(id = R.color.gray_top), colorResource(id = R.color.gray_bottom)))

    val bottomSheetScaffoldState =
        rememberBottomSheetScaffoldState(bottomSheetState = BottomSheetState(BottomSheetValue.Collapsed))
    val coroutineScope = rememberCoroutineScope()
    BottomSheetScaffold(
        sheetContent = {
            HomeBottomSheet(
                data = data,
                onItemClick = { viewModel.selectedWeatherDayEvent.value = it },
                onTabSelected = { viewModel.selectedAppSceneEvent.value = it },
                selectedUnit = selectedUnit,
                selectedItem = selectedDay,
                sceneState = sceneState,
                toggleFullDayData = {
                    coroutineScope.launch {
                        if (bottomSheetScaffoldState.bottomSheetState.isCollapsed) {
                            bottomSheetScaffoldState.bottomSheetState.expand()
                        } else {
                            bottomSheetScaffoldState.bottomSheetState.collapse()
                        }
                    }
                },
                isBottomSheetExpanded = bottomSheetScaffoldState.bottomSheetState.isCollapsed,
                buttonColor = colorResource(id = R.color.gray_bottom)
            )
        },
        sheetPeekHeight = bottomSheetPickHeight,
        sheetElevation = 24.dp,
        scaffoldState = bottomSheetScaffoldState,
        sheetShape = MaterialTheme.shapes.large.copy(
            bottomStart = CornerSize(0.dp),
            bottomEnd = CornerSize(0.dp)
        ),
    ) {
        HomeTile(
            modifier = Modifier
                .background(verticalGradientColor)
                .fillMaxWidth()
                .padding(bottom = bottomSheetPickHeight),
            data = selectedDay,
            selectedUnit = selectedUnit,
            onUnitChanged = { viewModel.updateSelectedUnit(it) }
        )
    }
}


@Composable
@Preview
private fun HomeScreenPreview() {
    HomeScreen(viewModel = AppViewModel())
}