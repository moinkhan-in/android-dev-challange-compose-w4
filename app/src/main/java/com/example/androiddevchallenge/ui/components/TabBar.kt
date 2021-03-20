package com.example.androiddevchallenge.ui.components

import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.androiddevchallenge.Scene

@Composable
fun TabBar(
    modifier: Modifier = Modifier,
    onTabItemSelected: (Scene) -> Unit
) {
    val tabItems = arrayListOf(Scene.Temperature, Scene.Precipitation, Scene.Precipitation)
    var tabSelectedIndex = 0

    TabRow(
        modifier = modifier,
        selectedTabIndex = tabSelectedIndex,

    ) {
        tabItems.forEachIndexed { index, item ->
            Tab(selected = false, onClick = {
                tabSelectedIndex = index
                onTabItemSelected.invoke(item)
            }) {
                Text(text = stringResource(item.toDisplay()))
            }
        }
    }
}